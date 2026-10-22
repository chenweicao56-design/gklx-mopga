package com.gklx.mopga.admin.module.ai.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import com.gklx.mopga.base.common.domain.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目表 分页查询表单
 *
 * @Author gklx
 * @Date 2026-10-19 14:28:17
 * @Copyright 1.0
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class ReqProjectQueryForm extends PageParam {

    @Schema(description = "项目编号（用户可自定义，智能体按此引用，如 PRJ-2026-001）")
    private String projectNo;

    @Schema(description = "项目类型（sys_dict 字典编码）")
    private String projectType;

    @Schema(description = "状态: 0-筹备,1-进行中,2-已完成,3-已暂停,4-已归档")
    private Integer status;
    //===以上自动生成，请不要随便修改===
}