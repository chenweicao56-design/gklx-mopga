package com.gklx.mopga.admin.module.requirement.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * 需求管理-MCP 列表查询参数
 *
 * @author gklx
 */
@Data
public class RequirementListQueryForm {

    @Schema(description = "日期(某日,按创建日期筛选,格式yyyy-MM-dd)")
    private LocalDate date;

    @Schema(description = "项目编码")
    private String projectCode;

    @Schema(description = "状态:1-待开发 2-开发中 3-测试中 4-已完成 5-已交付 6-失败")
    private Integer status;

    @Schema(description = "页码(默认1)")
    private Long pageNum;

    @Schema(description = "每页数量(默认50)")
    private Long pageSize;
}
