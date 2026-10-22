package com.gklx.mopga.admin.module.knowledge.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 知识库-自动分段处理器
 *
 * <p>一期：基于字符长度 + 段落边界的自动分段策略。
 * 参数：segment.length / segment.overlap 可配置。</p>
 *
 * @author gklx
 */
@Slf4j
@Component
public class KbSegmentProcessor {

    @Value("${kb.segment.length:500}")
    private int segmentLength;

    @Value("${kb.segment.overlap:50}")
    private int overlap;

    @Value("${kb.segment.min-length:20}")
    private int minLength;

    /**
     * 将纯文本切分为分段列表
     */
    public List<String> split(String text) {
        List<String> result = new ArrayList<>();
        if (!StringUtils.hasText(text)) {
            return result;
        }

        // 按换行分割成行，合并空行
        String[] lines = text.replace("\r\n", "\n").replace("\r", "\n").split("\n");
        StringBuilder current = new StringBuilder();

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                // 段落结束：flush 当前
                if (current.length() > 0) {
                    current.append('\n');
                }
                continue;
            }
            // 若当前段 + 下一行会超过长度，先 flush
            if (current.length() + trimmed.length() + 1 > segmentLength && current.length() > 0) {
                result.add(current.toString().trim());
                current.setLength(0);
                // overlap：将上一段末尾部分回溯
                appendOverlap(result, current);
            }
            current.append(trimmed).append('\n');
        }
        if (current.length() > 0) {
            result.add(current.toString().trim());
        }

        // 过滤过短分段（合并到上一段）
        return mergeShortSegments(result);
    }

    /**
     * 将 overlap 内容回溯追加到 current，保证衔接上下文
     */
    private void appendOverlap(List<String> result, StringBuilder current) {
        if (overlap <= 0 || result.isEmpty()) {
            return;
        }
        String last = result.get(result.size() - 1);
        if (last.length() > overlap) {
            current.append(last.substring(last.length() - overlap)).append('\n');
        } else {
            current.append(last).append('\n');
        }
    }

    /**
     * 合并过短分段
     */
    private List<String> mergeShortSegments(List<String> segments) {
        List<String> merged = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        for (String seg : segments) {
            if (buffer.length() > 0 && buffer.length() + seg.length() > segmentLength) {
                merged.add(buffer.toString());
                buffer.setLength(0);
            }
            if (buffer.length() > 0) {
                buffer.append('\n');
            }
            buffer.append(seg);
        }
        if (buffer.length() > 0) {
            merged.add(buffer.toString());
        }
        return merged;
    }
}
