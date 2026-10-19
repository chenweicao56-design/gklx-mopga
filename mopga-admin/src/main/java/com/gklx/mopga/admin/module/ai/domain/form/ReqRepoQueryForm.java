package com.gklx.mopga.admin.module.ai.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import com.gklx.mopga.base.common.domain.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工程（代码仓库）表 分页查询表单
 *
 * @Author gklx
 * @Date 2026-10-19 14:28:17
 * @Copyright 1.0
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class ReqRepoQueryForm extends PageParam {

    @Schema(description = "所属项目（关联 req_project.id）")
    private Long projectId;
    //===以上自动生成，请不要随便修改===
}