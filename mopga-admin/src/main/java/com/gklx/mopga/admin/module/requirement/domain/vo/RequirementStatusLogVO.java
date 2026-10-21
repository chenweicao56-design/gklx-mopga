package com.gklx.mopga.admin.module.requirement.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 需求管理-需求状态流转历史 VO
 *
 * @author gklx
 */
@Data
public class RequirementStatusLogVO {

    @Schema(description = "日志ID")
    private Long logId;

    @Schema(description = "需求ID")
    private Long requirementId;

    @Schema(description = "原状态")
    private Integer fromStatus;

    @Schema(description = "原状态描述")
    private String fromStatusDesc;

    @Schema(description = "目标状态")
    private Integer toStatus;

    @Schema(description = "目标状态描述")
    private String toStatusDesc;

    @Schema(description = "操作用户ID")
    private Long operator;

    @Schema(description = "操作用户名称")
    private String operatorName;

    @Schema(description = "来源:1-页面操作 2-MCP调用")
    private Integer source;

    @Schema(description = "来源描述")
    private String sourceDesc;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
