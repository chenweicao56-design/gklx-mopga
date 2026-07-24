package com.gklx.mopga.admin.module.ai.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * $技能版本文件快照表 列表VO
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:24
 * @Copyright 1.0
 */

@Data
public class SkillVersionFileVo {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "所属版本（关联 skill_version.id）")
    private Long versionId;

    @Schema(description = "文件相对路径（快照时刻）")
    private String filePath;

    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "文件类型")
    private String fileType;

    @Schema(description = "文件内容快照（不可变副本）")
    private String content;

    @Schema(description = "内容字节大小")
    private Integer contentSize;

    @Schema(description = "是否入口文件 SKILL.md")
    private Boolean isEntry;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除状态")
    private Boolean deletedFlag;
}