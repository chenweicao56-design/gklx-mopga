package com.gklx.mopga.admin.module.ai.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * $需求表 列表VO
 *
 * @Author gklx
 * @Date 2026-10-19 14:28:17
 * @Copyright 1.0
 */

@Data
public class ReqReqVo {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "所属项目（关联 req_project.id）")
    private Long projectId;

    @Schema(description = "需求编号（业务唯一，智能体按此引用，如 REQ-2026101901-001）")
    private String reqNo;

    @Schema(description = "需求标题/一句话描述")
    private String reqTitle;

    @Schema(description = "需求类型（sys_dict: 功能/优化/缺陷/重构/测试/文档/交付等）")
    private String reqType;

    @Schema(description = "优先级（sys_dict: P0紧急/P1高/P2中/P3低）")
    private String priority;

    @Schema(description = "状态: 0-待处理,1-开发中,2-测试中,3-已交付,4-已关闭,5-已驳回")
    private Integer status;

    @Schema(description = "需求详细描述（Markdown，供智能体读取开发）")
    private String content;

    @Schema(description = "验收标准（Markdown，供智能体自测与交付核验）")
    private String acceptanceCriteria;

    @Schema(description = "标签（JSON 数组）")
    private String tags;

    @Schema(description = "计划完成日期（支持按某天检索需求）")
    private LocalDate plannedDate;

    @Schema(description = "负责人（程序员）")
    private Long assignedUserId;

    @Schema(description = "父需求ID（支持需求拆分，关联 req_req.id）")
    private Long parentId;

    @Schema(description = "来源: manual-手动录入,agent-智能体生成,mcp-外部导入")
    private String source;

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