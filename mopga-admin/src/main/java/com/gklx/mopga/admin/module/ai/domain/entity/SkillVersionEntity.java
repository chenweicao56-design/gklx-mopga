package com.gklx.mopga.admin.module.ai.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 技能版本表 实体类
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:24
 * @Copyright 1.0
 */

@Data
@TableName("skill_version")
public class SkillVersionEntity {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 所属技能（关联 skill.id）
     */
    private Long skillId;

    /**
     * 版本号（语义化 MAJOR.MINOR.PATCH 或自增快照号）
     */
    private String versionNo;

    /**
     * 版本说明/变更摘要
     */
    private String remark;

    /**
     * 是否曾上线: 0-否,1-是
     */
    private Boolean isReleased;

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
}