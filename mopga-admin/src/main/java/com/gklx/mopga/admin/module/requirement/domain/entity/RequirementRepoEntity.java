package com.gklx.mopga.admin.module.requirement.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 需求管理-工程(git仓库)实体
 *
 * @author gklx
 */
@Data
@TableName("t_requirement_repo")
public class RequirementRepoEntity {

    /**
     * 工程ID
     */
    @TableId(type = IdType.AUTO)
    private Long repoId;

    /**
     * 归属项目ID
     */
    private Long projectId;

    /**
     * 工程/仓库名称
     */
    private String repoName;

    /**
     * git仓库地址(http/ssh)
     */
    private String repoUrl;

    /**
     * 默认分支
     */
    private String defaultBranch;

    /**
     * 本地克隆路径
     */
    private String localPath;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态:0-启用 1-停用
     */
    private Integer status;

    /**
     * 创建人ID
     */
    private Long createUserId;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
