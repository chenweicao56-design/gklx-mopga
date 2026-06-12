package com.gklx.mopga.admin.module.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import lombok.Data;

/**
 * 文档标签关联表 实体类
 *
 * @Author gklx
 * @Date 2026-06-12 09:54:56
 * @Copyright 1.0
 */

@Data
@TableName("t_document_tag_relation")
public class TDocumentTagRelationEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文档ID
     */
    private Long documentId;

    /**
     * 标签ID
     */
    private Long tagId;
}