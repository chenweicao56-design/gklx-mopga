package com.gklx.mopga.admin.module.knowledge.parser;

/**
 * 知识库-文档解析器
 *
 * <p>将各格式文件解析为纯文本，供后续自动分段。</p>
 *
 * @author gklx
 */
public interface KbFileParser {

    /**
     * 支持的扩展名（小写，不含点）
     */
    boolean support(String fileType);

    /**
     * 解析文件内容为纯文本
     *
     * @param data     文件字节
     * @param fileName 原始文件名（用于格式判断）
     */
    String parse(byte[] data, String fileName);
}
