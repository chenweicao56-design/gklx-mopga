package com.gklx.mopga.admin.module.requirement.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 需求管理-验收标准 VO
 *
 * @author gklx
 */
@Data
public class RequirementAcceptanceVO {

    @Schema(description = "验收标准内容")
    private String content;

    @Schema(description = "Given(前置条件)")
    private String given;

    @Schema(description = "When(操作)")
    private String when;

    @Schema(description = "Then(预期结果)")
    private String then;
}
