package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import com.gklx.mopga.base.common.domain.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 表 分页查询表单
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TableQueryForm extends PageParam {

    @Schema(description = "表名称/表注释")
    private String keyword;

    @Schema(description = "表名称")
    private String tableName;

    @Schema(description = "表名称")
    private String tableNames;

    @Schema(description = "数据库ID")
    private Long databaseId;

}
