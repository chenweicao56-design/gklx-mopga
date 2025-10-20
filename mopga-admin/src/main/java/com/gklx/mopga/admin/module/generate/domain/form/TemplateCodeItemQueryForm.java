package com.gklx.mopga.admin.module.generate.domain.form;

import com.gklx.mopga.base.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代码模板项表 分页查询表单
 *
 * @Author gklx
 * @Date 2025-09-18 17:05:30
 * @Copyright gklx
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TemplateCodeItemQueryForm extends PageParam {

    @Schema(description = "模板id")
    private Long templateId;

}
