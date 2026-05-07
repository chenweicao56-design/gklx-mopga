package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 表术语表 更新表单
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
public class TableTermUpdateForm {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "表主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "表主键 不能为空")
    private Long tableId;

    @Schema(description = "数据源id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "数据源id 不能为空")
    private Long databaseId;

    @Schema(description = "表注释")
    private String tableCommentTerm;

    @Schema(description = "场景")
    private String scenes;
}