package com.gklx.mopga.admin.module.ai.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 技能版本文件快照表 更新表单
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:24
 * @Copyright 1.0
 */

@Data
public class SkillVersionFileAddForm {

    @Schema(description = "所属版本（关联 skill_version.id）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "所属版本（关联 skill_version.id） 不能为空")
    private Long versionId;

    @Schema(description = "文件相对路径（快照时刻）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "文件相对路径（快照时刻） 不能为空")
    private String filePath;

    @Schema(description = "文件名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "文件名 不能为空")
    private String fileName;

    @Schema(description = "文件类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "文件类型 不能为空")
    private String fileType;

    @Schema(description = "文件内容快照（不可变副本）")
    private String content;

    @Schema(description = "内容字节大小", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "内容字节大小 不能为空")
    private Integer contentSize;

    @Schema(description = "是否入口文件 SKILL.md", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否入口文件 SKILL.md 不能为空")
    private Boolean isEntry;
}