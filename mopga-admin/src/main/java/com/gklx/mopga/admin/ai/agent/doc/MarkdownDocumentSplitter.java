package com.gklx.mopga.admin.ai.agent.doc;

import cn.hutool.core.util.StrUtil;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.segment.TextSegment;

import java.util.ArrayList;
import java.util.List;

public class MarkdownDocumentSplitter implements DocumentSplitter {
    @Override
    public List<TextSegment> split(Document document) {
        List<TextSegment> list = new ArrayList<>();
        String text = document.text();
        String[] split = text.split("\\R");

        StringBuilder segment = null;
        String title = "";
        for (String s : split) {
            if (s.startsWith("# ")) {
                title = s;
            } else {
                if (s.startsWith("## ")) {
                    if (StrUtil.isNotEmpty(segment)) {
                        TextSegment textSegment = TextSegment.from(StrUtil.isNotEmpty(title) ? title + segment : segment.toString());
                        list.add(textSegment);
                    }
                    segment = new StringBuilder();
                    segment.append(s);
                } else {
                    assert segment != null;
                    segment.append(s);
                }
            }

        }
        return list;
    }
}
