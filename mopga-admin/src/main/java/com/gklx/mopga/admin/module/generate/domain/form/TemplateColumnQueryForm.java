package com.gklx.mopga.admin.module.generate.domain.form;

import com.gklx.mopga.base.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 模板字段 分页查询表单
 *
 * @Author gklx
 * @Date 2025-09-26 15:45:20
 * @Copyright gklx
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TemplateColumnQueryForm extends PageParam {

    @Schema(description = "模板id")
    private Long templateId;

    @Schema(description = "数据库id")
    private Long databaseId;

    @Schema(description = "数据库id")
    private String keyword;
}
