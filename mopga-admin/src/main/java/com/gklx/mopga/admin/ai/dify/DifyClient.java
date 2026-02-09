package com.gklx.mopga.admin.ai.dify;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DifyClient implements IDatasetService {


    private final String URL = "http://localhost/v1";
    private final String DATASET_TOKEN = "dataset-jeS5CNyRK8L5DJPyBAeQdMHg";

    @Override
    public ResponseDTO<String> createDataset(Map<String, Object> params) {
        Map<String, Object> formParams = new HashMap<>();
        formParams.put("name", MapUtil.getStr(params, "name"));
        formParams.put("description", MapUtil.getStr(params, "description"));
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + DATASET_TOKEN);
        try (HttpResponse response = HttpRequest.post(URL)
                .addHeaders(headers)
                .body(JSONUtil.toJsonStr(formParams))
                .timeout(3000)
                .execute()) {
            if (response.getStatus() == HttpStatus.HTTP_OK) {
                JSONObject body = JSONUtil.parseObj(response.body());
                return ResponseDTO.ok(body.getStr("id"));
            } else {
                throw new BusinessException();
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public ResponseDTO<JSONArray> retrieve(Map<String, Object> params) {

        Map<String, Object> retrieval_model = MapUtil.<String, Object>builder()
                .put("search_method","hybrid_search")
                .put("top_k","3")
                .put("score_threshold_enabled",false)
                .build();
        Map<String, Object> formParams = MapUtil.<String, Object>builder()
                .put("query", MapUtil.getStr(params, "query"))
                .put("retrieval_model", retrieval_model)
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + DATASET_TOKEN);
        try (HttpResponse response = HttpRequest.post(String.format("%s/datasets/%s/retrieve", URL, MapUtil.getStr(params, "datasetId")))
                .addHeaders(headers)
                .body(JSONUtil.toJsonStr(formParams))
                .timeout(3000)
                .execute()) {
            if (response.getStatus() == HttpStatus.HTTP_OK) {
                JSONObject body = JSONUtil.parseObj(response.body());
                return ResponseDTO.ok(body.getJSONArray("records"));
            } else {
                throw new BusinessException();
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }
}
