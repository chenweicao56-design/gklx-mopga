package com.gklx.mopga.admin.module.requirement.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 需求管理-项目新建
 *
 * @author gklx
 */
@Data
public class RequirementProjectAddForm {

    @Schema(description = "项目名称")
    @NotBlank(message = "项目名称不能为空")
    @Length(max = 50, message = "项目名称最多50字符")
    private String projectName;

    @Schema(description = "项目编码(唯一,用于需求编号前缀)")
    @NotBlank(message = "项目编码不能为空")
    @Length(max = 32, message = "项目编码最多32字符")
    private String projectCode;

    @Schema(description = "项目描述")
    @Length(max = 500, message = "项目描述最多500字符")
    private String description;

    @Schema(description = "负责人姓名")
    @Length(max = 50, message = "负责人最多50字符")
    private String owner;

    @Schema(description = "创建人ID", hidden = true)
    private Long createUserId;

    @Schema(description = "创建人名称", hidden = true)
    private String createUserName;
}
