package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 代码模板项表 更新表单
 *
 * @Author gklx
 * @Date 2025-09-18 17:05:30
 * @Copyright gklx
 */

@Data
public class TemplateCodeItemAddForm {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "模板id")
    private Long templateId;

    @Schema(description = "文件名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "文件名称 不能为空")
    private String fileName;

    @Schema(description = "文件类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "文件类型 不能为空")
    private String fileType;

    @Schema(description = "文件路径", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "文件路径 不能为空")
    private String filePath;

    @Schema(description = "模板内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "模板内容 不能为空")
    private String content;

    @Schema(description = "排序")
    private Integer sort;
}
