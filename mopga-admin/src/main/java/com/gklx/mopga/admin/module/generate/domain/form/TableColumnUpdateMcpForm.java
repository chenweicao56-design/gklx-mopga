package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * $表 列表VO
 *
 * @Author gklx
 * @Date 2026-05-12 09:16:33
 * @Copyright 1.0
 */

@Data
public class TableColumnUpdateMcpForm {

    @Schema(description = "ID")
    private Long columnId;

    @Schema(description = "表id")
    private Long tableId;

    @Schema(description = "是否必填")
    private Boolean isRequired;

    @Schema(description = "新增接口是否要包含该字段")
    private Boolean isInsert;

    @Schema(description = "修改接口是否要包含该字段")
    private Boolean isUpdate;

    @Schema(description = "是否查询条件")
    private Boolean isWhere;

    @Schema(description = "查询类型：EQ(等于)/NE(不等于)/LIKE(模糊)/BETWEEN(范围)/LTE(小于等于)/LT(小于)/GTE(大于等于)/GT(大于)/Date(日期)/DateRange(日期范围)")
    private String whereType;


}