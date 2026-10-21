package com.gklx.mopga.admin.module.requirement.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 需求管理-项目 VO
 *
 * @author gklx
 */
@Data
public class RequirementProjectVO {

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "项目编码")
    private String projectCode;

    @Schema(description = "项目描述")
    private String description;

    @Schema(description = "负责人姓名")
    private String owner;

    @Schema(description = "状态:0-启用 1-停用")
    private Integer status;

    @Schema(description = "状态描述")
    private String statusDesc;

    @Schema(description = "工程数量")
    private Long repoCount;

    @Schema(description = "需求数量")
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
