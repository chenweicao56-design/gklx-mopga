package com.gklx.mopga.admin.module.ai.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * $技能草稿文件表 列表VO
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:23
 * @Copyright 1.0
 */

@Data
public class SkillFileVo {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "所属技能（关联 skill.id）")
    private Long skillId;

    @Schema(description = "文件相对路径（如 scripts/run.py，含文件名，目录为路径前缀）")
    private String filePath;

    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "文件类型（sys_dict: markdown/json/text/code/其他）")
    private String fileType;

    @Schema(description = "文件内容（文本/代码，存库）")
    private String content;

    @Schema(description = "内容字节大小")
    private Integer contentSize;

    @Schema(description = "是否入口文件 SKILL.md: 0-否,1-是")
    private Boolean isEntry;

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