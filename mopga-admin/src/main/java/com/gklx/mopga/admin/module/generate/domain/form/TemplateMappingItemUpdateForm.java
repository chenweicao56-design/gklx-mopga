package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 字段类型映射表 更新表单
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright gklx
 */

@Data
public class TemplateMappingItemUpdateForm {


    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ID 不能为空")
    private Long id;

    @Schema(description = "模板id")
    private Long templateId;

    @Schema(description = "数据源字段类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "数据源字段类型 不能为空")
    private String databaseColumnType;

    @Schema(description = "后端字段类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "后端字段类型 不能为空")
    private String backColumnType;

    @Schema(description = "前端字段类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "前端字段类型 不能为空")
    private String frontColumnType;

    @Schema(description = "前端组件")
    private String frontComponent;

    @Schema(description = "排序（越大越靠前）")
    private Integer sort;

}
