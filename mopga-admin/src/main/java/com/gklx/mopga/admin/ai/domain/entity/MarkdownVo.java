package com.gklx.mopga.admin.ai.domain.entity;

import lombok.Data;

import java.util.List;

@Data
public class MarkdownVo {
    private String id;
    private List<String> ids;

    private String md;
    private List<MarkdownEntity> originalData;
}
