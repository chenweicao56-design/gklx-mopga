package com.gklx.mopga.admin.module.knowledge.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 知识库-文档实体
 *
 * @author gklx
 */
@Data
@TableName("t_kb_document")
public class KbDocumentEntity {

    /**
     * 文档ID
     */
    @TableId(type = IdType.AUTO)
    private Long documentId;

    /**
     * 所属知识库ID
     */
    private Long baseId;

    /**
     * 原始文件名
     */
    private String fileName;

    /**
     * 文件存储路径/对象存储地址
     */
    private String fileUrl;

    /**
     * 文件字节数
     */
    private Long fileSize;

    /**
     * 扩展名: txt/md/pdf/docx/html
     */
    private String fileType;

    /**
     * 状态:0-上传中转 1-待解析 2-解析中 3-已解析 4-分段中 5-向量化中 6-已生效 7-解析失败 8-向量化失败
     */
    private Integer status;

    /**
     * 待解析队列顺序号
     */
    private Integer queueOrder;

    /**
     * 分段数量
     */
    private Integer chunkCount;

    /**
     * 解析后的纯文本
     */
    private String parsedContent;

    /**
     * 失败原因
     */
    private String errorMessage;

    /**
     * 解析重试次数
     */
    private Integer parseRetryCount;

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
