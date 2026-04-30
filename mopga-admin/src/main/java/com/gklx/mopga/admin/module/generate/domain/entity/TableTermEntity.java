package com.gklx.mopga.admin.module.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 表术语表 实体类
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
@TableName("ai_table_term")
public class TableTermEntity {

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
     * 表主键
     */
    private Long tableId;

    /**
     * 表注释
     */
    private String tableCommentTerm;

    /**
     * 场景
     */
    private String scenes;
}