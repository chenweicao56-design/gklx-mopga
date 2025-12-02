package com.gklx.mopga.admin.ai.agent.menu;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.gklx.mopga.admin.module.system.menu.domain.vo.MenuTreeVO;
import com.gklx.mopga.admin.module.system.menu.domain.vo.MenuVO;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuDocumentSplitter implements DocumentSplitter {
    @Override
    public List<TextSegment> split(Document document) {
        String text = document.text();
        MenuTreeVO bean = JSONUtil.toBean(text, MenuTreeVO.class);
        ArrayList<TextSegment> textSegments = new ArrayList<>();
        parseChildren(bean, textSegments);
        return textSegments;
    }

    @Override
    public List<TextSegment> splitAll(List<Document> documents) {
        ArrayList<TextSegment> textSegments = new ArrayList<>();
        documents.forEach(document -> {
            textSegments.addAll(split(document));
        });
        return textSegments;
    }

    @Override
    public List<TextSegment> splitAll(Document... documents) {
        ArrayList<TextSegment> textSegments = new ArrayList<>();
        Arrays.stream(documents).forEach(document -> {
            textSegments.addAll(split(document));
        });
        return textSegments;
    }

    private void parseChildren(MenuTreeVO bean, List<TextSegment> list) {
        List<MenuTreeVO> children = bean.getChildren();
        MenuVO menuVO = BeanUtil.copyProperties(bean, MenuVO.class);
        TextSegment textSegment = TextSegment.from(menuVO.getMenuName()+":"+JSONUtil.toJsonStr(menuVO), Metadata.from("id", bean.getMenuId().toString()));
        list.add(textSegment);
        if (CollectionUtil.isNotEmpty(children)) {
            for (MenuTreeVO child : children) {
                parseChildren(child, list);
            }
        }
    }
}
