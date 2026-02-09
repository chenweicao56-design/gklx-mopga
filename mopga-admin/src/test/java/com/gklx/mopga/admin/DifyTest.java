package com.gklx.mopga.admin;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONArray;
import com.gklx.mopga.admin.ai.dify.DifyClient;
import com.gklx.mopga.base.common.domain.ResponseDTO;

import java.util.Map;

public class DifyTest {

    public static void main(String[] args) {
        DifyClient difyClient = new DifyClient();

        Map<String, Object> build = MapUtil.<String, Object>builder()
                .put("query","事故")
                .put("datasetId","e79882f7-fcd8-49ec-a4be-cfeb8bb926a0")
                .build();
        ResponseDTO<JSONArray> retrieve = difyClient.retrieve(build);
        System.out.println(retrieve);
    }
}
