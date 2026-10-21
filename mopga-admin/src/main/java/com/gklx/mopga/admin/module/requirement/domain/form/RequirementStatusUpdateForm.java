package com.gklx.mopga.admin.module.requirement.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 需求管理-需求状态变更
 *
 * @author gklx
 */
@Data
public class RequirementStatusUpdateForm {

    @Schema(description = "需求ID")
    @NotNull(message = "需求ID不能为空")
    private Long requirementId;

    @Schema(description = "目标状态:1-待开发 2-开发中 3-测试中 4-已完成 5-已交付 6-失败")
    @NotNull(message = "目标状态不能为空")
    private Integer status;

    @Schema(description = "备注")
    @Length(max = 500, message = "备注最多500字符")
    private String remark;
}
