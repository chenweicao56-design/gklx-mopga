package com.gklx.mopga.admin.module.knowledge.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * 知识库-纯文本解析器（txt / md）
 *
 * @author gklx
 */
@Slf4j
@Component
public class KbTxtMdParser implements KbFileParser {

    @Override
    public boolean support(String fileType) {
        return "txt".equals(fileType) || "md".equals(fileType);
    }

    @Override
    public String parse(byte[] data, String fileName) {
        // 优先 UTF-8，若出现替换字符则回退 GBK
        String utf8 = new String(data, StandardCharsets.UTF_8);
        if (!utf8.contains("\uFFFD")) {
            return utf8;
        }
        return new String(data, java.nio.charset.Charset.forName("GBK"));
    }
}
