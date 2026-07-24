package com.gklx.mopga.admin.module.ai.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 技能主表 实体类
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:22
 * @Copyright 1.0
 */

@Data
@TableName("skill")
public class SkillEntity {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 技能名称（对应 SKILL.md frontmatter name，兼作引用标识）
     */
    private String name;

    /**
     * 技能描述（对应 SKILL.md frontmatter description）
     */
    private String description;

    /**
     * 分类（sys_dict 字典编码）
     */
    private String category;

    /**
     * 标签（JSON 数组，如 ["数据库","规范"]）
     */
    private String tags;

    /**
     * 状态: 0-草稿,1-已上线,2-已下线
     */
    private Integer status;

    /**
     * 当前线上生效版本ID（关联 skill_version.id）
     */
    private Long currentVersion;

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
     * 删除状态: 0-未删,1-已删
     */
    private Boolean deletedFlag;
}