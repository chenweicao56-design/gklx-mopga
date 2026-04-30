package com.gklx.mopga.admin.module.generate.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * $表术语表 列表VO
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
public class TableTermVo {

    @Schema(description = "表主键")
    private Long tableId;

    @Schema(description = "数据源id")
    private Long databaseId;

    @Schema(description = "表名称")
    private String tableName;

    @Schema(description = "表注释")
    private String tableComment;

    @Schema(description = "表注释")
    private String tableCommentTerm;

    @Schema(description = "场景")
    private String scenes;

    @Schema(description = "排序（越大越靠前）")
    private Integer sort;

    @Schema(description = "字段")
    private List<TableColumnTermVo> columns;
}