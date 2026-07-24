package com.gklx.mopga.admin.module.ai.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * $技能版本表 列表VO
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:24
 * @Copyright 1.0
 */

@Data
public class SkillVersionVo {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "所属技能（关联 skill.id）")
    private Long skillId;

    @Schema(description = "版本号（语义化 MAJOR.MINOR.PATCH 或自增快照号）")
    private String versionNo;

    @Schema(description = "版本说明/变更摘要")
    private String remark;

    @Schema(description = "是否曾上线: 0-否,1-是")
    private Boolean isReleased;

    @Schema(description = "创建人")
    private Long createUserId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新人")
    private Long updateUserId;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "删除状态")
    private Boolean deletedFlag;
}