package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 模板表 更新表单
 *
 * @Author gklx
 * @Date 2025-09-18 16:47:49
 * @Copyright gklx
 */

@Data
public class TemplateAddForm {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "模板名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "模板名称 不能为空")
    private String templateName;

    @Schema(description = "模板类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "模板类型 不能为空")
    private String templateType;

    @Schema(description = "数据源类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "数据源类型 不能为空")
    private String databaseType;

    @Schema(description = "语言类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "语言类型 不能为空")
    private String languageType;

    @Schema(description = "模板文件")
    private String templateFiles;

    @Schema(description = "项目路径")
    private String projectPath;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "排序")
    private Integer sort;
}
