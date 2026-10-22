package com.gklx.mopga.admin.module.knowledge.parser;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * 知识库-docx解析器（基于 POI）
 *
 * @author gklx
 */
@Slf4j
@Component
public class KbDocxParser implements KbFileParser {

    @Override
    public boolean support(String fileType) {
        return "docx".equals(fileType);
    }

    @Override
    public String parse(byte[] data, String fileName) {
        StringBuilder sb = new StringBuilder();
        try (XWPFDocument doc = new XWPFDocument(new ByteArrayInputStream(data))) {
            // 段落
            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                String text = paragraph.getText();
                if (text != null && !text.isBlank()) {
                    sb.append(text).append("\n");
                }
            }
            // 表格
            for (XWPFTable table : doc.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    List<String> cells = row.getTableCells().stream()
                            .map(XWPFTableCell::getText)
                            .toList();
                    sb.append(String.join("\t", cells)).append("\n");
                }
            }
        } catch (Exception e) {
            log.error("docx解析失败: {}", fileName, e);
            throw new RuntimeException("docx解析失败: " + e.getMessage(), e);
        }
        return sb.toString();
    }
}
