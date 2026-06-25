package com.gklx.mopga.admin.ai.model;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class EmbeddingModel {
    private final String model = "bge-m3-A4JQ3N6T";

    public Float[] embedding(String text) {
        JSONObject requestBody = new JSONObject();
        requestBody.set("model", model);
        requestBody.set("input", text);
//        requestBody.set("dimensions", 768);

        try (HttpResponse response = HttpRequest.post("http://10.169.100.49:9991/v1/embeddings")
                .header("Content-Type", "application/json")
                .body(requestBody.toString())
                .execute()) {

            String responseBody = response.body();
            JSONObject jsonResponse = JSONUtil.parseObj(responseBody);
            JSONArray data = jsonResponse.getJSONArray("data");
            if (CollectionUtil.isEmpty(data)) {
                return new Float[0];
            }
            JSONObject firstData = data.getJSONObject(0);
            JSONArray embeddingArray = firstData.getJSONArray("embedding");

            Float[] vector = new Float[embeddingArray.size()];
            for (int i = 0; i < embeddingArray.size(); i++) {
                vector[i] = embeddingArray.getBigDecimal(i).floatValue();
            }
            return vector;
        }
    }

    public Float[][] embedding(List<String> texts) {
        JSONObject requestBody = new JSONObject();
        requestBody.set("model", model);
        requestBody.set("input", texts);
//        requestBody.set("dimensions", 768);
        Float[][] res = new Float[texts.size()][];
        try (HttpResponse response = HttpRequest.post("http://10.169.100.49:9991/v1/embeddings")
                .header("Content-Type", "application/json")
                .body(requestBody.toString())
                .execute()) {

            String responseBody = response.body();
            JSONObject jsonResponse = JSONUtil.parseObj(responseBody);
            JSONArray data = jsonResponse.getJSONArray("data");
            if (CollectionUtil.isNotEmpty(data)) {
                for (int i = 0; i < data.size(); i++) {
                    JSONObject firstData = data.getJSONObject(i);
                    JSONArray embeddingArray = firstData.getJSONArray("embedding");

                    Float[] vector = new Float[embeddingArray.size()];
                    for (int j = 0; j < embeddingArray.size(); j++) {
                        vector[j] = embeddingArray.getBigDecimal(j).floatValue();
                    }
                    res[i] = vector;
                }
            } else {
                log.error("embedding error");
            }
            return res;
        }
    }
}
