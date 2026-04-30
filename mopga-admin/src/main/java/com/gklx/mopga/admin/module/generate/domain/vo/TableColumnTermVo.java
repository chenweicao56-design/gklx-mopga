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

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "创建人")
    private Long createUserId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新人")
    private Long updateUserId;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "删除状态")
    private Boolean deletedFlag;

    @Schema(description = "字段主键")
    private Long columnId;

    @Schema(description = "表术语主键")
    private Long tableTermId;

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