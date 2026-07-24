package com.gklx.mopga.admin.module.ai.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 技能版本表 更新表单
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:24
 * @Copyright 1.0
 */

@Data
public class SkillVersionUpdateForm {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ID 不能为空")
    private Long id;

    @Schema(description = "所属技能（关联 skill.id）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "所属技能（关联 skill.id） 不能为空")
    private Long skillId;

    @Schema(description = "版本号（语义化 MAJOR.MINOR.PATCH 或自增快照号）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "版本号（语义化 MAJOR.MINOR.PATCH 或自增快照号） 不能为空")
    private String versionNo;

    @Schema(description = "版本说明/变更摘要", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "版本说明/变更摘要 不能为空")
    private String remark;

    @Schema(description = "是否曾上线: 0-否,1-是", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否曾上线: 0-否,1-是 不能为空")
    private Boolean isReleased;

}