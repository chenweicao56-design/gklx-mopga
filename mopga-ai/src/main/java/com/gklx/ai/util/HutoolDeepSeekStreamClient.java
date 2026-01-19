package com.gklx.ai.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 使用 Hutool 流式调用 DeepSeek（深度求索）大模型
 */
public class HutoolDeepSeekStreamClient {
    // DeepSeek 官方 API 地址（流式/非流式通用）
    private static final String DEEPSEEK_API_URL = "https://api.deepseek.com/v1/chat/completions";
    // 替换为你的 DeepSeek API Key（从 DeepSeek 控制台获取）
    private static final String API_KEY = "sk-ae13e92d22164178bf5a27d84e1be35c";

    public static void main(String[] args) {
        // 1. 构造 DeepSeek 流式请求参数
        JSONObject requestBody = new JSONObject();
        // 指定 DeepSeek 模型（可选：deepseek-chat、deepseek-coder 等）
        requestBody.put("model", "deepseek-chat");
        requestBody.put("stream", true); // 开启流式响应
        requestBody.put("temperature", 0.7); // 随机性参数
        requestBody.put("max_tokens", 2048); // 最大生成长度

        // 构造对话消息（符合 OpenAI 格式）
        JSONArray messages = new JSONArray(); // 明确声明为 JSON 数组
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", "请详细介绍DeepSeek大模型的特点和优势");
        messages.add(userMessage); // 将消息对象添加到数组中
        requestBody.put("messages", messages); // 数组赋值给 messages 字段

        // 用于拼接最终完整回答
        StringBuilder finalAnswer = new StringBuilder();

        try (
                // 2. 发送 POST 请求并获取响应流
                HttpResponse response = HttpRequest.post(DEEPSEEK_API_URL)
                        // DeepSeek 认证方式：Authorization + Bearer Token
                        .header("Authorization", "Bearer " + API_KEY)
                        .header("Content-Type", "application/json")
                        .header("Accept", "text/event-stream") // 明确接收 SSE 格式
                        .body(requestBody.toString())
                        .execute();
                // 3. 逐行读取流式响应
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(response.bodyStream(), StandardCharsets.UTF_8)
                )
        ) {
            if (!response.isOk()) {
                System.err.println("请求失败，状态码：" + response.getStatus() + "，响应：" + response.body());
                return;
            }

            System.out.println("DeepSeek 流式回答：");
            String line;
            while ((line = reader.readLine()) != null) {
                // 过滤空行和结束标记
                if (line.isEmpty() || line.equals("data: [DONE]")) {
                    continue;
                }

                // 解析 SSE 格式数据（前缀为 data: ）
                if (line.startsWith("data: ")) {
                    String jsonStr = line.substring(6); // 去掉 "data: " 前缀
                    try {
                        JSONObject json = JSONUtil.parseObj(jsonStr);
                        // 提取增量内容（DeepSeek 格式和 OpenAI 完全一致）
                        String deltaContent = json.getJSONArray("choices")
                                .getJSONObject(0)
                                .getJSONObject("delta")
                                .getStr("content", "");

                        // 实时输出增量内容（逐字打印）
                        System.out.print(deltaContent);
                        // 拼接最终回答
                        finalAnswer.append(deltaContent);
                    } catch (Exception e) {
                        // 忽略单行解析异常（如非标准格式行）
                        continue;
                    }
                }
            }

            // 输出完整结果
            System.out.println("\n\n===== 完整回答 =====");
            System.out.println(finalAnswer.toString());

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("流式调用 DeepSeek 异常：" + e.getMessage());
        }
    }
}
