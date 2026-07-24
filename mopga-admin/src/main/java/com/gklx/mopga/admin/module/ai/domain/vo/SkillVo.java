package com.gklx.mopga.admin.module.ai.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * $技能主表 列表VO
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:22
 * @Copyright 1.0
 */

@Data
public class SkillVo {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "技能名称（对应 SKILL.md frontmatter name，兼作引用标识）")
    private String name;

    @Schema(description = "技能描述（对应 SKILL.md frontmatter description）")
    private String description;

    @Schema(description = "分类（sys_dict 字典编码）")
    private String category;

    @Schema(description = "标签（JSON 数组）")
    private String tags;

    @Schema(description = "状态: 0-草稿,1-已上线,2-已下线")
    private Integer status;

    @Schema(description = "当前线上生效版本ID（关联 skill_version.id）")
    private Long currentVersion;

    @Schema(description = "创建人")
    private Long createUserId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新人")
    private Long updateUserId;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "删除状态: 0-未删,1-已删")
    private Boolean deletedFlag;
}