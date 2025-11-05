package com.gklx.mopga.admin.module.generate.domain.form.sql;

import lombok.Data;

@Data
public class WhereForm {

    private String tableName;
    private String tableAlias;
    private String columnName;
    private String columnAlias;

    private String whereType;
    private String fieldName;
    private String fieldType;
}
