package com.gklx.mopga.admin.module.generate.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * $数据源术语表 列表VO
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
public class DatabaseTermVo {

    @Schema(description = "数据源id")
    private Long databaseId;

    @Schema(description = "名称")
    private String databaseName;

    @Schema(description = "别名")
    private String aliasName;

    @Schema(description = "类型")
    private String databaseType;

    @Schema(description = "术语")
    private String terms;

    @Schema(description = "数据源注释术语")
    private String databaseCommentTerm;

    @Schema(description = "表")
    private List<TableTermVo> tableTerms;
}