package com.gklx.mopga.admin.module.requirement.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 需求管理-需求实体
 *
 * @author gklx
 */
@Data
@TableName("t_requirement")
public class RequirementEntity {

    /**
     * 需求ID
     */
    @TableId(type = IdType.AUTO)
    private Long requirementId;

    /**
     * 需求编号(唯一,格式PREFIX+YYYYMMDD-XX)
     */
    private String reqNumber;

    /**
     * 归属项目ID
     */
    private Long projectId;

    /**
     * 需求标题
     */
    private String title;

    /**
     * 富文本需求描述
     */
    private String content;

    /**
     * 验收标准列表(JSON数组)
     */
    private String acceptanceCriteria;

    /**
     * 状态:1-待开发 2-开发中 3-测试中 4-已完成 5-已交付 6-失败
     */
    private Integer status;

    /**
     * 创建人ID
     */
    private Long createBy;

    /**
     * 创建人名称
     */
    private String createByName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
