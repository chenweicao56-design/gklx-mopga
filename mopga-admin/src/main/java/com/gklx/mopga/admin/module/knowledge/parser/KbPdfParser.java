package com.gklx.mopga.admin.module.knowledge.parser;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 知识库-PDF解析器（基于 PDFBox）
 *
 * @author gklx
 */
@Slf4j
@Component
public class KbPdfParser implements KbFileParser {

    @Override
    public boolean support(String fileType) {
        return "pdf".equals(fileType);
    }

    @Override
    public String parse(byte[] data, String fileName) {
        try {
            try (PDDocument document = PDDocument.load(data)) {
                if (document.isEncrypted()) {
                    throw new IllegalArgumentException("PDF已加密，暂不支持解析");
                }
                PDFTextStripper stripper = new PDFTextStripper();
                return stripper.getText(document);
            }
        } catch (Exception e) {
            log.error("PDF解析失败: {}", fileName, e);
            throw new RuntimeException("PDF解析失败: " + e.getMessage(), e);
        }
    }
}
