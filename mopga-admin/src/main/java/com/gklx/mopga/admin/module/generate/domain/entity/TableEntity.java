package com.gklx.mopga.admin.module.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 表 实体类
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
@TableName("gen_table")
public class TableEntity {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long tableId;

    /**
     * 数据源Id
     */
    private Long databaseId;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 删除状态
     */
    private Boolean deletedFlag;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 排序（越大越靠前）
     */
    private Integer sort;

    /**
     * 后端作者
     */
    private String backendAuthor;

    /**
     * 后端日期
     */
    private LocalDateTime backendDate;

    /**
     * 版权
     */
    private String copyright;

    /**
     * 前端作者
     */
    private String frontAuthor;

    /**
     * 前端日期
     */
    private LocalDateTime frontDate;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 逻辑删除
     */
    private Boolean isPhysicallyDeleted;

    /**
     * 单词名
     */
    private String wordName;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 子表Id
     */
    private Long subTableId;

    /**
     * 表前缀
     */
    private String tablePrefix;

    /**
     * 扩展字段
     */
    private String extendedData;

    /**
     * 是否分页（1是）
     */
    private Boolean isPage;

    /**
     * 是否详情（1是）
     */
    private Boolean isDetail;

    /**
     * 是否增加（1是）
     */
    private Boolean isAdd;

    /**
     * 是否修改（1是）
     */
    private Boolean isUpdate;

    /**
     * 是否删除（1是）
     */
    private Boolean isDelete;

    /**
     * 是否批量删除（1是）
     */
    private Boolean isBatchDelete;

    /**
     * 编辑组件
     */
    private String editComponent;

    /**
     * 每行几个表单
     */
    private Integer formCountLine;

    /**
     * 是否是树
     */
    private Boolean isTree;
}
