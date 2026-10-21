package com.gklx.mopga.admin.module.requirement.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 需求管理-项目实体
 *
 * @author gklx
 */
@Data
@TableName("t_requirement_project")
public class RequirementProjectEntity {

    /**
     * 项目ID
     */
    @TableId(type = IdType.AUTO)
    private Long projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目编码(唯一,用于需求编号前缀)
     */
    private String projectCode;

    /**
     * 项目描述
     */
    private String description;

    /**
     * 负责人姓名
     */
    private String owner;

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
