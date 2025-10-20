package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gklx.mopga.admin.module.generate.domain.form.TranslationForm;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "翻译")
public class TranslationController {

    @Value("${mopga.translate.url}")
    private String url;
    @Value("${mopga.translate.appId}")
    private String appId;
    @Value("${mopga.translate.secret}")
    private String secret;


    @Operation(summary = "翻译 @author gklx")
    @PostMapping("/translate")
    @SaCheckPermission("translate:query")
    public ResponseDTO<String> queryPage(@RequestBody TranslationForm form) {

        Map<String, Object> params = new HashMap<>();
        params.put("q", form.getContent());
        params.put("from", form.getFrom());
        params.put("to", form.getTo());
        params.put("appid", appId);
        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);
        // 签名
        String src = appId + form.getContent() + salt + secret;
        params.put("sign", DigestUtil.md5Hex(src));
        String s = HttpUtil.get(url, params);
        JSONObject jsonObject = JSONUtil.parseObj(s);
        JSONArray transResult = jsonObject.getJSONArray("trans_result");
        String dst = transResult.getJSONObject(0).getStr("dst");
        return ResponseDTO.ok(dst);
    }

}
