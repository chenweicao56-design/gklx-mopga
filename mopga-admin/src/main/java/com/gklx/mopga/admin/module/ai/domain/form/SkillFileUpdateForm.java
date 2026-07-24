package com.gklx.mopga.admin.module.ai.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 技能草稿文件表 更新表单
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:23
 * @Copyright 1.0
 */

@Data
public class SkillFileUpdateForm {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ID 不能为空")
    private Long id;

    @Schema(description = "文件相对路径（如 scripts/run.py，含文件名，目录为路径前缀）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "文件相对路径（如 scripts/run.py，含文件名，目录为路径前缀） 不能为空")
    private String filePath;

    @Schema(description = "文件名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "文件名 不能为空")
    private String fileName;

    @Schema(description = "文件类型（sys_dict: markdown/json/text/code/其他）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "文件类型（sys_dict: markdown/json/text/code/其他） 不能为空")
    private String fileType;

    @Schema(description = "文件内容（文本/代码，存库）")
    private String content;

    @Schema(description = "内容字节大小", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "内容字节大小 不能为空")
    private Integer contentSize;

    @Schema(description = "是否入口文件 SKILL.md: 0-否,1-是", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否入口文件 SKILL.md: 0-否,1-是 不能为空")
    private Boolean isEntry;

}