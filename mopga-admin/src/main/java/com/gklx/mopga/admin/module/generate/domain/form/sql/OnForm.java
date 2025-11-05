package com.gklx.mopga.admin.module.generate.domain.form.sql;

import lombok.Data;

@Data
public class OnForm {

    private String tableName;
    private String tableAlias;
    private String columnName;
    private String columnAlias;

    private String targetTableName;
    private String targetTableAlias;
    private String targetColumnName;
    private String targetColumnAlias;

    private String type;
}
