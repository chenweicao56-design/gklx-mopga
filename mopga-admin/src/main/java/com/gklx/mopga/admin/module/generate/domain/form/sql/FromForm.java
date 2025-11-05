package com.gklx.mopga.admin.module.generate.domain.form.sql;

import lombok.Data;

import java.util.List;

@Data
public class FromForm {

    private String joinType;

    private String tableName;
    private String tableAlias;

    private List<OnForm> ons;

}
