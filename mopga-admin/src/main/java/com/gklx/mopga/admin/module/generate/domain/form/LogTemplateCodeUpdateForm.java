package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 模版代码日志表 更新表单
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
public class LogTemplateCodeUpdateForm {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "主键 不能为空")
    private Long id;

    @Schema(description = "创建人", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "创建人 不能为空")
    private Long createUserId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "创建时间 不能为空")
    private LocalDateTime createTime;

    @Schema(description = "模版代码主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "模版代码主键 不能为空")
    private Long templateCodeId;

    @Schema(description = "代码内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "代码内容 不能为空")
    private String content;

    @Schema(description = "类型（1：新增，2：修改，3：删除）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "类型（1：新增，2：修改，3：删除） 不能为空")
    private String type;

}