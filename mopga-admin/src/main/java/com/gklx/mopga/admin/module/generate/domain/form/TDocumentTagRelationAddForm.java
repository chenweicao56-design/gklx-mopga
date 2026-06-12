package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 文档标签关联表 更新表单
 *
 * @Author gklx
 * @Date 2026-06-12 09:54:56
 * @Copyright 1.0
 */

@Data
public class TDocumentTagRelationAddForm {

    @Schema(description = "文档ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "文档ID 不能为空")
    private Long documentId;

    @Schema(description = "标签ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "标签ID 不能为空")
    private Long tagId;
}