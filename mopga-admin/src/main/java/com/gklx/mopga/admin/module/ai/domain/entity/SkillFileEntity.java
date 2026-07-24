package com.gklx.mopga.admin.module.ai.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 技能草稿文件表 实体类
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:23
 * @Copyright 1.0
 */

@Data
@TableName("skill_file")
public class SkillFileEntity {

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
     * 文件相对路径（如 scripts/run.py，含文件名，目录为路径前缀）
     */
    private String filePath;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型（sys_dict: markdown/json/text/code/其他）
     */
    private String fileType;

    /**
     * 文件内容（文本/代码，存库）
     */
    private String content;

    /**
     * 内容字节大小
     */
    private Integer contentSize;

    /**
     * 是否入口文件 SKILL.md: 0-否,1-是
     */
    private Boolean isEntry;

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