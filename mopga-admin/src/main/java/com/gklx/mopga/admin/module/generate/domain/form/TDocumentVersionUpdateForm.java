package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 文档版本历史表 更新表单
 *
 * @Author gklx
 * @Date 2026-06-12 09:55:03
 * @Copyright 1.0
 */

@Data
public class TDocumentVersionUpdateForm {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "主键ID 不能为空")
    private Long id;

    @Schema(description = "文档ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "文档ID 不能为空")
    private Long documentId;

    @Schema(description = "版本号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "版本号 不能为空")
    private String version;

    @Schema(description = "文档名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "文档名称 不能为空")
    private String documentName;

    @Schema(description = "文档内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "文档内容 不能为空")
    private String documentContent;

    @Schema(description = "文件类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "文件类型 不能为空")
    private String fileType;

    @Schema(description = "文档类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "文档类型 不能为空")
    private String documentType;

    @Schema(description = "创建人", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "创建人 不能为空")
    private String createBy;

}