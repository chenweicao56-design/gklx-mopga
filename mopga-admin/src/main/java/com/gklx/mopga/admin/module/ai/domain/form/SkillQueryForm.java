package com.gklx.mopga.admin.module.ai.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import com.gklx.mopga.base.common.domain.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 技能主表 分页查询表单
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:22
 * @Copyright 1.0
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class SkillQueryForm extends PageParam {

    @Schema(description = "技能名称（对应 SKILL.md frontmatter name，兼作引用标识）")
    private String name;

    @Schema(description = "分类（sys_dict 字典编码）")
    private String category;

    @Schema(description = "标签（JSON 数组）")
    private String tags;
}