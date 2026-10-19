package com.gklx.mopga.admin.module.ai.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 工程（代码仓库）表 更新表单
 *
 * @Author gklx
 * @Date 2026-10-19 14:28:17
 * @Copyright 1.0
 */

@Data
public class ReqRepoAddForm {

    @Schema(description = "所属项目（关联 req_project.id）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "所属项目（关联 req_project.id） 不能为空")
    private Long projectId;

    @Schema(description = "工程/仓库名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "工程/仓库名称 不能为空")
    private String repoName;

    @Schema(description = "工程类型（sys_dict: 前端/后端/服务/库等）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "工程类型（sys_dict: 前端/后端/服务/库等） 不能为空")
    private String repoType;

    @Schema(description = "代码仓库地址（git url）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "代码仓库地址（git url） 不能为空")
    private String repoUrl;

    @Schema(description = "默认分支", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "默认分支 不能为空")
    private String branch;

    @Schema(description = "本地代码目录（供智能体读写）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "本地代码目录（供智能体读写） 不能为空")
    private String localPath;

    @Schema(description = "工程描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "工程描述 不能为空")
    private String description;

    @Schema(description = "技术栈（如 Java/Spring Boot/MyBatis-Plus）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "技术栈（如 Java/Spring Boot/MyBatis-Plus） 不能为空")
    private String techStack;

    @Schema(description = "状态: 0-草稿,1-启用,2-停用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "状态: 0-草稿,1-启用,2-停用 不能为空")
    private Integer status;

}