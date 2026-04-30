package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 数据源术语表 更新表单
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
public class DatabaseTermUpdateForm {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "术语")
    private String terms;

    @Schema(description = "数据源id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "数据源id 不能为空")
    private Long databaseId;

    @Schema(description = "数据源注释术语")
    private String databaseCommentTerm;

}