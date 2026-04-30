package com.gklx.mopga.admin.module.generate.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * $表字段术语 列表VO
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
public class TableColumnTermVo {

    @Schema(description = "字段主键")
    private Long columnId;

    @Schema(description = "字段名称")
    private String columnName;

    @Schema(description = "字段注释")
    private String columnComment;

    @Schema(description = "是否主键（1是）")
    private Boolean isPk;

    @Schema(description = "是否自增（1是）")
    private Boolean isIncrement;

    @Schema(description = "是否为空（1是）")
    private Boolean isNull;

    @Schema(description = "默认值")
    private String columnDefault;

    @Schema(description = "字段类型")
    private String columnType;

    @Schema(description = "表注释")
    private String columnCommentTerm;

    @Schema(description = "字典值")
    private String dicts;

    @Schema(description = "样例值")
    private String example;

    @Schema(description = "外键")
    private String foreignKey;

    @Schema(description = "术语")
    private String terms;
}