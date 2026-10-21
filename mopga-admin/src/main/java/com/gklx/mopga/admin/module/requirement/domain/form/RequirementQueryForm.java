package com.gklx.mopga.admin.module.requirement.domain.form;

import com.gklx.mopga.base.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

/**
 * 需求管理-需求查询
 *
 * @author gklx
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RequirementQueryForm extends PageParam {

    @Schema(description = "需求编号(关键字)")
    @Length(max = 32, message = "需求编号最多32字符")
    private String reqNumber;

    @Schema(description = "需求标题(关键字)")
    @Length(max = 100, message = "需求标题最多100字符")
    private String title;

    @Schema(description = "归属项目ID")
    private Long projectId;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "开始日期")
    private LocalDate startTime;

    @Schema(description = "结束日期")
    private LocalDate endTime;
}
