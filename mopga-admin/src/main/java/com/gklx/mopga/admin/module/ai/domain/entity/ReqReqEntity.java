package com.gklx.mopga.admin.module.ai.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 需求表 实体类
 *
 * @Author gklx
 * @Date 2026-10-19 14:28:17
 * @Copyright 1.0
 */

@Data
@TableName("req_req")
public class ReqReqEntity {

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
     * 需求编号（业务唯一，智能体按此引用，如 REQ-2026101901-001）
     */
    private String reqNo;

    /**
     * 需求标题/一句话描述
     */
    private String reqTitle;

    /**
     * 需求类型（sys_dict: 功能/优化/缺陷/重构/测试/文档/交付等）
     */
    private String reqType;

    /**
     * 优先级（sys_dict: P0紧急/P1高/P2中/P3低）
     */
    private String priority;

    /**
     * 状态: 0-待处理,1-开发中,2-测试中,3-已交付,4-已关闭,5-已驳回
     */
    private Integer status;

    /**
     * 需求详细描述（Markdown，供智能体读取开发）
     */
    private String content;

    /**
     * 验收标准（Markdown，供智能体自测与交付核验）
     */
    private String acceptanceCriteria;

    /**
     * 标签（JSON 数组）
     */
    private String tags;

    /**
     * 计划完成日期（支持按某天检索需求）
     */
    private LocalDate plannedDate;

    /**
     * 负责人（程序员）
     */
    private Long assignedUserId;

    /**
     * 父需求ID（支持需求拆分，关联 req_req.id）
     */
    private Long parentId;

    /**
     * 来源: manual-手动录入,agent-智能体生成,mcp-外部导入
     */
    private String source;

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