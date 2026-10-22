package com.gklx.mopga.admin.module.knowledge.manager;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 知识库-自动分段处理器 单元测试
 */
class KbSegmentProcessorTest {

    private KbSegmentProcessor newProcessor(int length, int overlap, int minLength) {
        KbSegmentProcessor processor = new KbSegmentProcessor();
        ReflectionTestUtils.setField(processor, "segmentLength", length);
        ReflectionTestUtils.setField(processor, "overlap", overlap);
        ReflectionTestUtils.setField(processor, "minLength", minLength);
        return processor;
    }

    @Test
    void testEmptyAndBlank() {
        KbSegmentProcessor processor = newProcessor(500, 50, 20);
        assertTrue(processor.split(null).isEmpty());
        assertTrue(processor.split("").isEmpty());
        assertTrue(processor.split("   \n  ").isEmpty());
    }

    @Test
    void testShortTextSingleSegment() {
        KbSegmentProcessor processor = newProcessor(500, 50, 20);
        String text = "这是一段很短的测试文本。";
        List<String> result = processor.split(text);
        assertEquals(1, result.size());
        assertTrue(result.get(0).contains("测试"));
    }

    @Test
    void testLongTextWithoutNewline() {
        // 无换行超长文本，应按长度切分
        KbSegmentProcessor processor = newProcessor(20, 5, 3);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("汉字编号").append(i).append(" ");
        }
        List<String> result = processor.split(sb.toString());
        assertFalse(result.isEmpty());
        // 所有分段加起来应能找回大部分原文（含重叠）
        StringBuilder joined = new StringBuilder();
        for (String s : result) {
            joined.append(s);
        }
        assertTrue(joined.length() >= sb.length());
    }

    @Test
    void testParagraphBoundary() {
        KbSegmentProcessor processor = newProcessor(100, 10, 5);
        String text = "第一段内容。" + "\n" + "第二段内容。" + "\n" + "第三段内容。";
        List<String> result = processor.split(text);
        assertFalse(result.isEmpty());
        // 分段内容应包含各段关键词
        String all = String.join("", result);
        assertTrue(all.contains("第一段"));
        assertTrue(all.contains("第二段"));
        assertTrue(all.contains("第三段"));
    }

    @Test
    void testOrderPreserved() {
        KbSegmentProcessor processor = newProcessor(500, 50, 20);
        String text = "A段句子。\nB段句子。\nC段句子。";
        List<String> result = processor.split(text);
        int a = indexOfContain(result, "A段");
        int b = indexOfContain(result, "B段");
        int c = indexOfContain(result, "C段");
        assertTrue(a != -1 && b != -1 && c != -1, "分段应包含各段关键内容");
        assertTrue(a <= b && b <= c, "分段顺序应保持");
    }

    private int indexOfContain(List<String> list, String keyword) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(keyword)) {
                return i;
            }
        }
        return -1;
    }
}
