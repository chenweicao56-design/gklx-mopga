package com.gklx.mopga.admin.module.requirement.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 需求管理-项目编辑
 *
 * @author gklx
 */
@Data
public class RequirementProjectUpdateForm {

    @Schema(description = "项目ID")
    @NotNull(message = "项目ID不能为空")
    private Long projectId;

    @Schema(description = "项目名称")
    @NotNull(message = "项目名称不能为空")
    @Length(max = 50, message = "项目名称最多50字符")
    private String projectName;

    @Schema(description = "项目描述")
    @Length(max = 500, message = "项目描述最多500字符")
    private String description;

    @Schema(description = "负责人姓名")
    @Length(max = 50, message = "负责人最多50字符")
    private String owner;

    @Schema(description = "状态:0-启用 1-停用")
    @NotNull(message = "状态不能为空")
    private Integer status;
}
