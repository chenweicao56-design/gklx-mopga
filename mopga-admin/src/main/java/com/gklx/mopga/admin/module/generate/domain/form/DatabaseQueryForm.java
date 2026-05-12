package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import com.gklx.mopga.base.common.domain.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据源表 分页查询表单
 *
 * @Author gklx
 * @Date 2026-05-07 10:58:06
 * @Copyright 1.0
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class DatabaseQueryForm extends PageParam {

    @Schema(description = "别名")
    private String aliasName;

    @Schema(description = "名称")
    private String databaseName;
}