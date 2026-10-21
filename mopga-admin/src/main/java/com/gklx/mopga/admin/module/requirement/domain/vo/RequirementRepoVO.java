package com.gklx.mopga.admin.module.requirement.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 需求管理-工程(git仓库) VO
 *
 * @author gklx
 */
@Data
public class RequirementRepoVO {

    @Schema(description = "工程ID")
    private Long repoId;

    @Schema(description = "归属项目ID")
    private Long projectId;

    @Schema(description = "归属项目名称")
    private String projectName;

    @Schema(description = "归属项目编码")
    private String projectCode;

    @Schema(description = "工程/仓库名称")
    private String repoName;

    @Schema(description = "git仓库地址")
    private String repoUrl;

    @Schema(description = "默认分支")
    private String defaultBranch;

    @Schema(description = "本地克隆路径")
    private String localPath;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "状态:0-启用 1-停用")
    private Integer status;

    @Schema(description = "状态描述")
    private String statusDesc;

    @Schema(description = "关联需求数量")
    private Long requirementCount;

    @Schema(description = "创建人ID")
    private Long createUserId;

    @Schema(description = "创建人名称")
    private String createUserName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
