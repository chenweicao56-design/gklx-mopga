package com.gklx.mopga.admin.module.ai.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 技能版本文件快照表 实体类
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:24
 * @Copyright 1.0
 */

@Data
@TableName("skill_version_file")
public class SkillVersionFileEntity {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 所属版本（关联 skill_version.id）
     */
    private Long versionId;

    /**
     * 文件相对路径（快照时刻）
     */
    private String filePath;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件内容快照（不可变副本）
     */
    private String content;

    /**
     * 内容字节大小
     */
    private Integer contentSize;

    /**
     * 是否入口文件 SKILL.md
     */
    private Boolean isEntry;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 删除状态
     */
    private Boolean deletedFlag;
}