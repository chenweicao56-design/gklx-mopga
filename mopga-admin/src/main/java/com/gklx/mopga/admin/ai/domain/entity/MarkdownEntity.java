package com.gklx.mopga.admin.ai.domain.entity;

import lombok.Data;

import java.util.List;

@Data
public class MarkdownEntity {

    private String id;
    private String page_number;
    private List<Double> bbox;
    private List<Double> page_size;
    private String md;
    private String type;
}
