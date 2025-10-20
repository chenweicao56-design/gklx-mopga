package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 映射表 更新表单
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
public class MappingUpdateForm {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "主键 不能为空")
    private Long id;

    @Schema(description = "映射名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "映射名称 不能为空")
    private String name;

    @Schema(description = "映射编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "映射编码 不能为空")
    private String code;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "排序")
    private Integer sort;

}
