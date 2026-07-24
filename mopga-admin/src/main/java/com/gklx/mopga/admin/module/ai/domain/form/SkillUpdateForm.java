package com.gklx.mopga.admin.module.ai.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 技能主表 更新表单
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:22
 * @Copyright 1.0
 */

@Data
public class SkillUpdateForm {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ID 不能为空")
    private Long id;

    @Schema(description = "技能名称（对应 SKILL.md frontmatter name，兼作引用标识）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "技能名称（对应 SKILL.md frontmatter name，兼作引用标识） 不能为空")
    private String name;

    @Schema(description = "技能描述（对应 SKILL.md frontmatter description）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "技能描述（对应 SKILL.md frontmatter description） 不能为空")
    private String description;

    @Schema(description = "分类（sys_dict 字典编码）")
    private String category;

    @Schema(description = "标签（JSON 数组）")
    private String tags;

    @Schema(description = "当前线上生效版本ID（关联 skill_version.id）")
    private Long currentVersion;

}