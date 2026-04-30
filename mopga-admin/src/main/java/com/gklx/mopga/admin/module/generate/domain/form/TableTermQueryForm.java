package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import com.gklx.mopga.base.common.domain.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 表术语表 分页查询表单
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TableTermQueryForm extends PageParam {

    @Schema(description = "表主键")
    private Long tableId;
}