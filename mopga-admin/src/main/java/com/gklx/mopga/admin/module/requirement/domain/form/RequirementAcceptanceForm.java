package com.gklx.mopga.admin.module.requirement.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 需求管理-验收标准项
 *
 * @author gklx
 */
@Data
public class RequirementAcceptanceForm {

    @Schema(description = "验收标准内容")
    @Length(max = 1000, message = "验收标准最多1000字符")
    private String content;

    @Schema(description = "Given(前置条件,可选)")
    @Length(max = 500, message = "Given最多500字符")
    private String given;

    @Schema(description = "When(操作,可选)")
    @Length(max = 500, message = "When最多500字符")
    private String when;

    @Schema(description = "Then(预期结果,可选)")
    @Length(max = 500, message = "Then最多500字符")
    private String then;
}
