package com.gklx.mopga.admin.ai.model;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.List;

public class RerankModel {

    public List<JSONObject> rerank(String text, List<JSONObject> documents, Integer topN) {

        List<String> content = documents.stream().map(document -> document.getStr("content")).toList();
        JSONObject requestBody = new JSONObject();
        requestBody.set("model", "bce-reranker-base_v1");
        requestBody.set("query", text);
        requestBody.set("documents", content);
        requestBody.set("top_n", topN);
        requestBody.set("return_documents", false);

        try (HttpResponse response = HttpRequest.post("http://10.169.100.49:9991/v1/rerank")
                .header("Content-Type", "application/json")
                .body(requestBody.toString())
                .execute()) {
            List<JSONObject> res = new ArrayList<>();
            String responseBody = response.body();
            JSONObject jsonObject = JSONUtil.parseObj(responseBody);
            JSONArray results = jsonObject.getJSONArray("results");
            if (CollectionUtil.isNotEmpty(results)) {
                for (int i = 0; i < results.size(); i++) {
                    JSONObject item = results.getJSONObject(i);
                    Integer index = item.getInt("index");
                    Float relevanceScore = item.getFloat("relevance_score");
                    JSONObject document = documents.get(index);
                    document.set("relevance_score", relevanceScore);
                    res.add(document);
                }
            }
            return res;
        }
    }
}
