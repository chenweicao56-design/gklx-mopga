package com.gklx.mopga.admin.module.ai.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import com.gklx.mopga.base.common.domain.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 技能版本表 分页查询表单
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:24
 * @Copyright 1.0
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class SkillVersionQueryForm extends PageParam {

    @Schema(description = "所属技能（关联 skill.id）")
    private Long skillId;

    @Schema(description = "版本号（语义化 MAJOR.MINOR.PATCH 或自增快照号）")
    private String versionNo;
}