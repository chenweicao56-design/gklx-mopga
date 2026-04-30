package com.gklx.ai.text2sql.dto;

import lombok.Data;

import java.util.List;

@Data
public class Column {

    private String tableId;

    private String columnId;

    private String columnName;

    private String columnType;

    private String columnComment;

    private List<String> dicties;

    private List<String> samples;

    private List<String> terms;

    private List<String> attentions;
}
