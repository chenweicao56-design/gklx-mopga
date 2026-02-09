package com.gklx.mopga.admin.ai.dify;

import cn.hutool.json.JSONArray;
import com.gklx.mopga.base.common.domain.ResponseDTO;

import java.util.Map;

public interface IDatasetService {

    ResponseDTO<String> createDataset(Map<String, Object> params);

    ResponseDTO<JSONArray> retrieve(Map<String, Object> params);

}
