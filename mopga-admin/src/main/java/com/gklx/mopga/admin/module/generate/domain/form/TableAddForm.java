package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 表 更新表单
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
public class TableAddForm {

    @Schema(description = "数据源Id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "数据源Id 不能为空")
    private Long databaseId;

    @Schema(description = "表名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "表名称 不能为空")
    private String tableName;

    @Schema(description = "表注释")
    private String tableComment;

    @Schema(description = "排序（越大越靠前）")
    private Integer sort;

    @Schema(description = "后端作者", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "后端作者 不能为空")
    private String backendAuthor;

    @Schema(description = "后端日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "后端日期 不能为空")
    private LocalDateTime backendDate;

    @Schema(description = "版权", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "版权 不能为空")
    private String copyright;

    @Schema(description = "前端作者", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "前端作者 不能为空")
    private String frontAuthor;

    @Schema(description = "前端日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "前端日期 不能为空")
    private LocalDateTime frontDate;

    @Schema(description = "包名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "包名 不能为空")
    private String packageName;

    @Schema(description = "模块名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "模块名 不能为空")
    private String moduleName;

    @Schema(description = "逻辑删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "逻辑删除 不能为空")
    private Boolean isPhysicallyDeleted;

    @Schema(description = "单词名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "单词名 不能为空")
    private String wordName;

    @Schema(description = "表前缀")
    private String tablePrefix;

    @Schema(description = "扩展字段")
    private String extendedData;

    @Schema(description = "是否分页（1是）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否分页（1是） 不能为空")
    private Boolean isPage;

    @Schema(description = "是否详情（1是）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否详情（1是） 不能为空")
    private Boolean isDetail;

    @Schema(description = "是否增加（1是）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否增加（1是） 不能为空")
    private Boolean isAdd;

    @Schema(description = "是否修改（1是）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否修改（1是） 不能为空")
    private Boolean isUpdate;

    @Schema(description = "是否删除（1是）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否删除（1是） 不能为空")
    private Boolean isDelete;

    @Schema(description = "是否批量删除（1是）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否批量删除（1是） 不能为空")
    private Boolean isBatchDelete;

    @Schema(description = "编辑组件", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "编辑组件 不能为空")
    private String editComponent;

    @Schema(description = "每行几个表单", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "每行几个表单 不能为空")
    private Integer formCountLine;

    @Schema(description = "是否是树", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否是树 不能为空")
    private Boolean isTree;
}