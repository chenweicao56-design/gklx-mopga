package com.gklx.mopga.admin.module.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 文档版本历史表 实体类
 *
 * @Author gklx
 * @Date 2026-06-12 09:55:03
 * @Copyright 1.0
 */

@Data
@TableName("t_document_version")
public class TDocumentVersionEntity {

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
     * 版本号
     */
    private String version;

    /**
     * 文档名称
     */
    private String documentName;

    /**
     * 文档内容
     */
    private String documentContent;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文档类型
     */
    private String documentType;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}