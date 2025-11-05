package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 模板字段 更新表单
 *
 * @Author gklx
 * @Date 2025-09-26 15:45:20
 * @Copyright gklx
 */

@Data
public class TemplateColumnUpdateForm {


    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ID 不能为空")
    private Long columnId;

    @Schema(description = "模板id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "模板id 不能为空")
    private Long templateId;

    @Schema(description = "字段名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "字段名称 不能为空")
    private String columnName;

    @Schema(description = "字段注释", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "字段注释 不能为空")
    private String columnComment;

    @Schema(description = "是否主键（1是）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否主键（1是） 不能为空")
    private Boolean isPk;

    @Schema(description = "是否自增（1是）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否自增（1是） 不能为空")
    private Boolean isIncrement;

    @Schema(description = "是否为空（1是）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否为空（1是） 不能为空")
    private Boolean isNull;

    @Schema(description = "默认值")
    private String columnDefault;

    @Schema(description = "字段类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "字段类型 不能为空")
    private String columnType;

    @Schema(description = "排序")
    private Integer sort;

    /**
     * 是否基类字段
     */
    private Boolean isBase;
}
