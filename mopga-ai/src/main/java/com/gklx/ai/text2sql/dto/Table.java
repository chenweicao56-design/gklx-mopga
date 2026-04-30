package com.gklx.ai.text2sql.dto;

import lombok.Data;

import java.util.List;

@Data
public class Table {

    private String tableId;

    private String tableName;

    private String tableComment;

    private List<String> scenes;

    private List<Column> columns;

}
