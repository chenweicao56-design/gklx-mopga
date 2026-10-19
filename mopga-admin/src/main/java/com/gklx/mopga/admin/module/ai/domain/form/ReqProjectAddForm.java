package com.gklx.mopga.admin.module.ai.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * 项目表 更新表单
 *
 * @Author gklx
 * @Date 2026-10-19 14:28:17
 * @Copyright 1.0
 */

@Data
public class ReqProjectAddForm {

    @Schema(description = "项目编号（用户可自定义，智能体按此引用，如 PRJ-2026-001）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "项目编号（用户可自定义，智能体按此引用，如 PRJ-2026-001） 不能为空")
    private String projectNo;

    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "项目名称 不能为空")
    private String projectName;

    @Schema(description = "项目描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "项目描述 不能为空")
    private String description;

    @Schema(description = "项目类型（sys_dict 字典编码）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "项目类型（sys_dict 字典编码） 不能为空")
    private String projectType;

    @Schema(description = "项目负责人（关联员工表）")
    private Long ownerUserId;

    @Schema(description = "计划开始日期")
    private LocalDate startDate;

    @Schema(description = "计划结束日期")
    private LocalDate endDate;

    @Schema(description = "状态: 0-筹备,1-进行中,2-已完成,3-已暂停,4-已归档", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "状态: 0-筹备,1-进行中,2-已完成,3-已暂停,4-已归档 不能为空")
    private Integer status;

    @Schema(description = "标签（JSON 数组）")
    private String tags;

    @Schema(description = "备注", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "备注 不能为空")
    private String remark;

}