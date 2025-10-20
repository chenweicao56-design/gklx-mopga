package com.gklx.mopga.admin.module.generate.jdbc.pojo;

import lombok.Data;

import java.util.List;

@Data
public class SqlDefines {

    /**
     * 数据源类型
     */
    private String databaseType;

    /**
     * 获取数据库表sql
     */
    private List<SqlItem> tableSqlList;
    /**
     * 获取数据库表字段sql
     */
    private List<SqlItem> tableFieldSqlList;
}
