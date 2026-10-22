package com.gklx.mopga.admin.module.knowledge.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 知识库-文件解析器工厂
 *
 * <p>根据文件扩展名分发到对应解析器。</p>
 *
 * @author gklx
 */
@Slf4j
@Component
public class KbFileParserFactory {

    private final List<KbFileParser> parsers;

    public KbFileParserFactory(List<KbFileParser> parsers) {
        this.parsers = parsers;
    }

    /**
     * 解析文件为纯文本
     *
     * @param data     文件字节
     * @param fileName 原始文件名
     */
    public String parse(byte[] data, String fileName) {
        String fileType = getFileType(fileName);
        for (KbFileParser parser : parsers) {
            if (parser.support(fileType)) {
                return parser.parse(data, fileName);
            }
        }
        throw new IllegalArgumentException("暂不支持的文件类型: " + fileType);
    }

    /**
     * 校验扩展名是否支持（xml/html/pdf/docx/txt/md）
     */
    public boolean support(String fileName) {
        String fileType = getFileType(fileName);
        for (KbFileParser parser : parsers) {
            if (parser.support(fileType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 提取小写扩展名
     */
    public String getFileType(String fileName) {
        if (StringUtils.hasText(fileName) && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        }
        return "";
    }
}
