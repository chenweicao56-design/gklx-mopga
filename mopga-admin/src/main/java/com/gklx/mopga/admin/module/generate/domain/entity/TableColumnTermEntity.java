package com.gklx.mopga.admin.module.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 表字段术语 实体类
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
@TableName("ai_table_column_term")
public class TableColumnTermEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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
     * 字段主键
     */
    private Long columnId;

    /**
     * 数据源id
     */
    private Long databaseId;

    /**
     * 表术语主键
     */
    private Long tableTermId;

    /**
     * 表注释
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String columnCommentTerm;

    /**
     * 字典值
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String dicts;

    /**
     * 样例值
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String example;

    /**
     * 外键
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String foreignKey;

    /**
     * 术语
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String terms;
}