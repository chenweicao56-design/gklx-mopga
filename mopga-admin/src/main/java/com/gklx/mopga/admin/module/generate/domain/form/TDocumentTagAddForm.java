package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 标签表 更新表单
 *
 * @Author gklx
 * @Date 2026-06-12 09:54:50
 * @Copyright 1.0
 */

@Data
public class TDocumentTagAddForm {

    @Schema(description = "标签名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "标签名称 不能为空")
    private String tagName;

    @Schema(description = "是否系统标签(0否/1是)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否系统标签(0否/1是) 不能为空")
    private Integer isSystem;

    @Schema(description = "创建人", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "创建人 不能为空")
    private String createBy;
}