package com.gklx.mopga.admin.module.requirement.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 需求管理-需求状态流转历史实体
 *
 * @author gklx
 */
@Data
@TableName("t_requirement_status_log")
public class RequirementStatusLogEntity {

    /**
     * 日志ID
     */
    @TableId(type = IdType.AUTO)
    private Long logId;

    /**
     * 需求ID
     */
    private Long requirementId;

    /**
     * 原状态
     */
    private Integer fromStatus;

    /**
     * 目标状态
     */
    private Integer toStatus;

    /**
     * 操作用户ID
     */
    private Long operator;

    /**
     * 操作用户名称
     */
    private String operatorName;

    /**
     * 来源:1-页面操作 2-MCP调用
     */
    private Integer source;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
