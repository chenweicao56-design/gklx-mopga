package com.gklx.mopga.admin.module.generate.domain.form.sql;

import lombok.Data;

import java.util.List;

@Data
public class SqlForm {
    private Long databaseId;
    private String functionName;
    private List<SelectForm> selects;
    private String mainTableName;
    private String mainTableAlias;
    private List<FromForm> froms;
    private List<WhereForm> wheres;
    private List<GroupByForm> groupBys;
    private LimitForm limit;
}
