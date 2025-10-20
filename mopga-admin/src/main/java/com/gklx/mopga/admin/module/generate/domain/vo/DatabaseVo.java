package com.gklx.mopga.admin.module.generate.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * $数据源表 列表VO
 *
 * @Author ${.backendAuthor}
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
public class DatabaseVo {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "创建人")
    private Long createUserId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新人")
    private Long updateUserId;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "删除状态")
    private Boolean deletedFlag;

    @Schema(description = "名称")
    private String databaseName;

    @Schema(description = "类型")
    private String databaseType;

    @Schema(description = "语言类型")
    private String languageType;

    @Schema(description = "状态")
    private String databaseStatus;

    @Schema(description = "业务分类id")
    private Integer categoryId;

    @Schema(description = "数据源地址")
    private String url;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "超时时间")
    private Integer timeout;

    @Schema(description = "后端作者")
    private String backendAuthor;

    @Schema(description = "后端日期")
    private LocalDateTime backendDate;

    @Schema(description = "后端项目路径")
    private String backendProjectPath;

    @Schema(description = "前端作者")
    private String frontAuthor;

    @Schema(description = "前端日期")
    private LocalDateTime frontDate;

    @Schema(description = "前端项目路径")
    private String frontProjectPath;

    @Schema(description = "版权")
    private String copyright;

    @Schema(description = "包名")
    private String packageName;

    @Schema(description = "模块名")
    private String moduleName;

    @Schema(description = "表扩展字段")
    private String tableExtendedData;

    @Schema(description = "字段扩展字段")
    private String columnExtendedData;

    @Schema(description = "逻辑删除")
    private Boolean isPhysicallyDeleted;

    @Schema(description = "模板id")
    private Long templateId;

    @Schema(description = "表前缀")
    private String tablePrefix;

    @Schema(description = "是否分页（1是）")
    private Boolean isPage;

    @Schema(description = "是否详情（1是）")
    private Boolean isDetail;

    @Schema(description = "是否增加（1是）")
    private Boolean isAdd;

    @Schema(description = "是否修改（1是）")
    private Boolean isUpdate;

    @Schema(description = "是否删除（1是）")
    private Boolean isDelete;

    @Schema(description = "是否批量删除（1是）")
    private Boolean isBatchDelete;

    @Schema(description = "编辑组件")
    private String editComponent;

    @Schema(description = "每行几个表单")
    private Integer formCountLine;

}