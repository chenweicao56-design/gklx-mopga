package com.gklx.mopga.admin.module.generate.jdbc.pojo;

import lombok.Data;

import java.util.List;

@Data
public class SqlItem {
    private String sql;

    private List<String> columns;
}
