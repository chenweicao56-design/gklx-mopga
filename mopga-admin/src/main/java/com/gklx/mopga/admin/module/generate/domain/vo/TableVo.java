package com.gklx.mopga.admin.module.generate.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * $表 列表VO
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
public class TableVo {

    @Schema(description = "ID")
    private Long tableId;

    @Schema(description = "数据源Id")
    private Long databaseId;

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

    @Schema(description = "表名称")
    private String tableName;

    @Schema(description = "表注释")
    private String tableComment;

    @Schema(description = "排序（越大越靠前）")
    private Integer sort;

    @Schema(description = "后端作者")
    private String backendAuthor;

    @Schema(description = "后端日期")
    private LocalDateTime backendDate;

    @Schema(description = "版权")
    private String copyright;

    @Schema(description = "前端作者")
    private String frontAuthor;

    @Schema(description = "前端日期")
    private LocalDateTime frontDate;

    @Schema(description = "包名")
    private String packageName;

    @Schema(description = "模块名")
    private String moduleName;

    @Schema(description = "逻辑删除")
    private Boolean isPhysicallyDeleted;

    @Schema(description = "单词名")
    private String wordName;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "子表Id")
    private Long subTableId;

    @Schema(description = "表前缀")
    private String tablePrefix;

    @Schema(description = "扩展字段")
    private String extendedData;

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

    @Schema(description = "是否是树")
    private Boolean isTree;

    @Schema(description = "小驼峰")
    private String lowerCamelCase;

    @Schema(description = "大驼峰")
    private String upperCamelCase;

    @Schema(description = "下划线")
    private String snakeCase;

    @Schema(description = "中横线")
    private String kabadCase;


    @Schema(description = "字段列表")
    private List<GenTableColumnVo> columns;


}
