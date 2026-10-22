package com.gklx.mopga.admin.module.knowledge.parser;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * 知识库-HTML解析器（基于 jsoup）
 *
 * @author gklx
 */
@Slf4j
@Component
public class KbHtmlParser implements KbFileParser {

    @Override
    public boolean support(String fileType) {
        return "html".equals(fileType) || "htm".equals(fileType);
    }

    @Override
    public String parse(byte[] data, String fileName) {
        try {
            String html = new String(data, StandardCharsets.UTF_8);
            Document document = Jsoup.parse(html);
            // 移除脚本/样式
            document.select("script, style, noscript").remove();
            return document.body() == null ? "" : document.body().text();
        } catch (Exception e) {
            log.error("html解析失败: {}", fileName, e);
            throw new RuntimeException("html解析失败: " + e.getMessage(), e);
        }
    }
}
