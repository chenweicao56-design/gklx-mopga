package com.gklx.mopga.admin.module.generate.domain.form;

import com.gklx.mopga.base.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字段类型映射表 分页查询表单
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright gklx
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TemplateMappingItemQueryForm extends PageParam {

    @Schema(description = "模板ID")
    private Long templateId;

}
