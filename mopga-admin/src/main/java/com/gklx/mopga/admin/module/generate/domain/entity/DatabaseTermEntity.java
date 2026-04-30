package com.gklx.mopga.admin.module.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 数据源术语表 实体类
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
@TableName("ai_database_term")
public class DatabaseTermEntity {

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
     * 术语
     */
    private String terms;

    /**
     * 数据源id
     */
    private Long databaseId;

    /**
     * 数据源注释术语
     */
    private String databaseCommentTerm;
}