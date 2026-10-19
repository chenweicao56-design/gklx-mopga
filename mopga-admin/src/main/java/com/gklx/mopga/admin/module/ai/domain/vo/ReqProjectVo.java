package com.gklx.mopga.admin.module.ai.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * $项目表 列表VO
 *
 * @Author gklx
 * @Date 2026-10-19 14:28:17
 * @Copyright 1.0
 */

@Data
public class ReqProjectVo {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "项目编号（用户可自定义，智能体按此引用，如 PRJ-2026-001）")
    private String projectNo;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "项目描述")
    private String description;

    @Schema(description = "项目类型（sys_dict 字典编码）")
    private String projectType;

    @Schema(description = "项目负责人（关联员工表）")
    private Long ownerUserId;

    @Schema(description = "计划开始日期")
    private LocalDate startDate;

    @Schema(description = "计划结束日期")
    private LocalDate endDate;

    @Schema(description = "状态: 0-筹备,1-进行中,2-已完成,3-已暂停,4-已归档")
    private Integer status;

    @Schema(description = "标签（JSON 数组）")
    private String tags;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建人")
    private Long createUserId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新人")
    private Long updateUserId;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "删除状态: 0-未删,1-已删")
    private Boolean deletedFlag;
    //===以上自动生成，请不要随便修改===  
}