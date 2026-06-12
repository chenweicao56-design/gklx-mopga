package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 文档主表 更新表单
 *
 * @Author gklx
 * @Date 2026-06-12 09:54:33
 * @Copyright 1.0
 */

@Data
public class TDocumentAddForm {

    @Schema(description = "文档名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "文档名称 不能为空")
    private String documentName;

    @Schema(description = "文档内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "文档内容 不能为空")
    private String documentContent;

    @Schema(description = "文件类型(markdown/json/text/code等)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "文件类型(markdown/json/text/code等) 不能为空")
    private String fileType;

    @Schema(description = "文档类型(技术文档/用户手册/配置文件等)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "文档类型(技术文档/用户手册/配置文件等) 不能为空")
    private String documentType;

    @Schema(description = "版本号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "版本号 不能为空")
    private String version;

    @Schema(description = "创建人", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "创建人 不能为空")
    private String createBy;

    @Schema(description = "更新人")
    private String updateBy;
}