package com.gklx.mopga.admin.module.ai.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * 需求表 更新表单
 *
 * @Author gklx
 * @Date 2026-10-19 14:28:17
 * @Copyright 1.0
 */

@Data
public class ReqReqUpdateForm {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ID 不能为空")
    private Long id;

    @Schema(description = "所属项目（关联 req_project.id）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "所属项目（关联 req_project.id） 不能为空")
    private Long projectId;

    @Schema(description = "需求编号（业务唯一，智能体按此引用，如 REQ-2026101901-001）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "需求编号（业务唯一，智能体按此引用，如 REQ-2026101901-001） 不能为空")
    private String reqNo;

    @Schema(description = "需求标题/一句话描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "需求标题/一句话描述 不能为空")
    private String reqTitle;

    @Schema(description = "需求类型（sys_dict: 功能/优化/缺陷/重构/测试/文档/交付等）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "需求类型（sys_dict: 功能/优化/缺陷/重构/测试/文档/交付等） 不能为空")
    private String reqType;

    @Schema(description = "优先级（sys_dict: P0紧急/P1高/P2中/P3低）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "优先级（sys_dict: P0紧急/P1高/P2中/P3低） 不能为空")
    private String priority;

    @Schema(description = "状态: 0-待处理,1-开发中,2-测试中,3-已交付,4-已关闭,5-已驳回", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "状态: 0-待处理,1-开发中,2-测试中,3-已交付,4-已关闭,5-已驳回 不能为空")
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

    @Schema(description = "来源: manual-手动录入,agent-智能体生成,mcp-外部导入", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "来源: manual-手动录入,agent-智能体生成,mcp-外部导入 不能为空")
    private String source;
    //===以上自动生成，请不要随便修改===
}