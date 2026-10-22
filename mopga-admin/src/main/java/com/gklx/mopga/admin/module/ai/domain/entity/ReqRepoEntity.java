package com.gklx.mopga.admin.module.ai.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 工程（代码仓库）表 实体类
 *
 * @Author gklx
 * @Date 2026-10-19 14:28:17
 * @Copyright 1.0
 */

@Data
@TableName("req_repo")
public class ReqRepoEntity {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 所属项目（关联 req_project.id）
     */
    private Long projectId;

    /**
     * 工程/仓库名称
     */
    private String repoName;

    /**
     * 工程类型（sys_dict: 前端/后端/服务/库等）
     */
    private String repoType;

    /**
     * 代码仓库地址（git url）
     */
    private String repoUrl;

    /**
     * 默认分支
     */
    private String branch;

    /**
     * 本地代码目录（供智能体读写）
     */
    private String localPath;

    /**
     * 工程描述
     */
    private String description;

    /**
     * 技术栈（如 Java/Spring Boot/MyBatis-Plus）
     */
    private String techStack;

    /**
     * 状态: 0-草稿,1-启用,2-停用
     */
    private Integer status;

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