package com.gklx.mopga.admin.module.knowledge.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 知识库-知识库实体
 *
 * @author gklx
 */
@Data
@TableName("t_kb_base")
public class KbBaseEntity {

    /**
     * 知识库ID
     */
    @TableId(type = IdType.AUTO)
    private Long baseId;

    /**
     * 知识库名称(唯一)
     */
    private String name;

    /**
     * 知识库描述
     */
    private String description;

    /**
     * 文档数量(冗余)
     */
    private Integer docCount;

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
