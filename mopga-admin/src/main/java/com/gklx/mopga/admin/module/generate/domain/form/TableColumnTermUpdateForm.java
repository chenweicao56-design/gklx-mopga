package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 表字段术语 更新表单
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
public class TableColumnTermUpdateForm {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "主键 不能为空")
    private Long id;

    @Schema(description = "字段主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "字段主键 不能为空")
    private Long columnId;

    @Schema(description = "表术语主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "表术语主键 不能为空")
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