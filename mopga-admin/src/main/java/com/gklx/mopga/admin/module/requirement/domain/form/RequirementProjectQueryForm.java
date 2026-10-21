package com.gklx.mopga.admin.module.requirement.domain.form;

import com.gklx.mopga.base.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 需求管理-项目查询
 *
 * @author gklx
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RequirementProjectQueryForm extends PageParam {

    @Schema(description = "项目名称(关键字)")
    @Length(max = 50, message = "项目名称最多50字符")
    private String projectName;

    @Schema(description = "项目编码")
    @Length(max = 32, message = "项目编码最多32字符")
    private String projectCode;

    @Schema(description = "状态:0-启用 1-停用")
    private Integer status;
}
