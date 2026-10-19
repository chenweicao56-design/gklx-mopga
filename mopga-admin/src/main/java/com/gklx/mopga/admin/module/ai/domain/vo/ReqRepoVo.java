package com.gklx.mopga.admin.module.ai.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * $工程（代码仓库）表 列表VO
 *
 * @Author gklx
 * @Date 2026-10-19 14:28:17
 * @Copyright 1.0
 */

@Data
public class ReqRepoVo {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "所属项目（关联 req_project.id）")
    private Long projectId;

    @Schema(description = "工程/仓库名称")
    private String repoName;

    @Schema(description = "工程类型（sys_dict: 前端/后端/服务/库等）")
    private String repoType;

    @Schema(description = "代码仓库地址（git url）")
    private String repoUrl;

    @Schema(description = "默认分支")
    private String branch;

    @Schema(description = "本地代码目录（供智能体读写）")
    private String localPath;

    @Schema(description = "工程描述")
    private String description;

    @Schema(description = "技术栈（如 Java/Spring Boot/MyBatis-Plus）")
    private String techStack;

    @Schema(description = "状态: 0-草稿,1-启用,2-停用")
    private Integer status;

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
    //===以上自动生成，请不要随便修改===  
}