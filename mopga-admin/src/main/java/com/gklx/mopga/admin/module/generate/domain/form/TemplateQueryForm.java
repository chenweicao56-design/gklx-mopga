package com.gklx.mopga.admin.module.generate.domain.form;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gklx.mopga.base.common.domain.PageParam;
import com.gklx.mopga.base.common.json.deserializer.DictDataDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 模板表 分页查询表单
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TemplateQueryForm extends PageParam {

    @Schema(description = "模板名称")
    private String templateName;

    @Schema(description = "数据源类型")
    @JsonDeserialize(using = DictDataDeserializer.class)
    private String databaseType;

    @Schema(description = "语言类型")
    @JsonDeserialize(using = DictDataDeserializer.class)
    private String languageType;

    @Schema(description = "模板类型")
    @JsonDeserialize(using = DictDataDeserializer.class)
    private String templateType;

}
