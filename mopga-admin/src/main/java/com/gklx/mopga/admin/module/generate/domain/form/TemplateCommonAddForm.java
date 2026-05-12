package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 公共模板 更新表单
 *
 * @Author gklx
 * @Date 2026-05-12 09:44:02
 * @Copyright 1.0
 */

@Data
public class TemplateCommonAddForm {


    @Schema(description = "模板名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "模板名称 不能为空")
    private String templateName;

    @Schema(description = "模板内容")
    private String content;

    @Schema(description = "文件类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "文件类型 不能为空")
    private String fileType;

    @Schema(description = "类别")
    private String category;

    @Schema(description = "排序")
    private Integer sort;
}