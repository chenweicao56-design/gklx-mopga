package com.gklx.mopga.admin.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.map.MapUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gklx.mopga.base.common.exception.BusinessException;
import com.gklx.mopga.base.module.support.file.domain.vo.FileDownloadVO;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PaddleOcrClient {

    //    private final String API_URL = "https://yad5m13bp0v6rdye.aistudio-app.com/layout-parsing";
//    private final String TOKEN = "0068c3a61cca3dd5b24d352b853992f4a66ca661";
    private final String API_URL = "https://cfrcnewca6c9qac7.aistudio-app.com/layout-parsing";
    private final String TOKEN = "763cc011dc29b6d0cda8914de75142ae6a3053ad";

    private final int FILE_TYPE = 1;

    public String run(FileDownloadVO fileDownloadVO) {
        StringBuilder res = new StringBuilder();
        try {
            String fileBase64 = Base64.encode(fileDownloadVO.getData());
            Map<String, Object> payload = MapUtil.<String, Object>builder()
                    .put("file", fileBase64)
                    .put("fileType", FILE_TYPE)
                    .put("useDocOrientationClassify", false)
                    .put("useDocUnwarping", false)
                    .put("useChartRecognition", false)
                    .build();
            JSONObject responseJson = sendPostRequest(API_URL, TOKEN, payload);
            if (responseJson == null) {
                throw new BusinessException("API请求失败");
            }
            JSONObject result = responseJson.getJSONObject("result");
            JSONArray layoutParsingResults = result.getJSONArray("layoutParsingResults");
            for (int i = 0; i < layoutParsingResults.size(); i++) {
                JSONObject jsonObject = layoutParsingResults.getJSONObject(i);
                JSONObject markdown = jsonObject.getJSONObject("markdown");
                if (i > 0) res.append("\n");
                res.append(markdown.getStr("text"));
            }


            // 5. 解析响应并保存Markdown/图片到本地
            return res.toString();

        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
    }

    /**
     * 发送JSON格式POST请求（与原逻辑一致，适配网络图片）
     */
    private JSONObject sendPostRequest(String apiUrl, String token, Map<String, Object> payload) {
        try (HttpResponse response = HttpRequest.post(apiUrl).header("Authorization", "token " + token).body(JSONUtil.toJsonStr(payload)).header(Header.CONTENT_TYPE, "application/json").execute()) {

            if (response.getStatus() != 200) {
                System.err.println("API请求失败，状态码：" + response.getStatus() + "，响应：" + response.body());
                return null;
            }
            return JSONUtil.parseObj(response.body());
        } catch (Exception e) {
            System.err.println("发送HTTP请求异常：" + e.getMessage());
            return null;
        }
    }

}
