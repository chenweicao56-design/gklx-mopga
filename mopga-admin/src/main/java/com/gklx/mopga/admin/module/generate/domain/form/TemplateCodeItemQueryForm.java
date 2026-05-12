package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import com.gklx.mopga.base.common.domain.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代码模板项表 分页查询表单
 *
 * @Author gklx
 * @Date 2026-05-12 09:52:37
 * @Copyright 1.0
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TemplateCodeItemQueryForm extends PageParam {

    @Schema(description = "模板id")
    private Long templateId;

}