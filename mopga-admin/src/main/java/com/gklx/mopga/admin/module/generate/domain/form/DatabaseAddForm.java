package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 数据源表 更新表单
 *
 * @Author gklx
 * @Date 2025-09-19 13:48:59
 * @Copyright gklx
 */

@Data
public class DatabaseAddForm {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "名称 不能为空")
    private String databaseName;

    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "类型 不能为空")
    private String databaseType;

    @Schema(description = "语言类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "语言类型 不能为空")
    private String languageType;

    @Schema(description = "状态")
    private String databaseStatus;

    @Schema(description = "业务分类id")
    private Integer categoryId;

    @Schema(description = "数据源地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "数据源地址 不能为空")
    private String url;

    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户名 不能为空")
    private String userName;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码 不能为空")
    private String password;

    @Schema(description = "超时时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "超时时间 不能为空")
    private Integer timeout;

    @Schema(description = "后端作者", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "后端作者 不能为空")
    private String backendAuthor;

    @Schema(description = "后端日期")
    private LocalDateTime backendDate;

    @Schema(description = "版权", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "版权 不能为空")
    private String copyright;

    @Schema(description = "前端作者", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "前端作者 不能为空")
    private String frontAuthor;

    @Schema(description = "前端日期")
    private LocalDateTime frontDate;

    @Schema(description = "包名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "包名 不能为空")
    private String packageName;

    @Schema(description = "模块名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "模块名 不能为空")
    private String moduleName;

    @Schema(description = "表扩展字段")
    private String tableExtendedData;

    @Schema(description = "字段扩展字段")
    private String columnExtendedData;

    @Schema(description = "逻辑删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "逻辑删除 不能为空")
    private Boolean isPhysicallyDeleted;

    @Schema(description = "模板id")
    private Long templateId;

    @Schema(description = "表前缀")
    private String tablePrefix;

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
}
