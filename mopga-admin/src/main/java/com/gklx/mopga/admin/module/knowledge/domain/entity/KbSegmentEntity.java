package com.gklx.mopga.admin.module.knowledge.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 知识库-分段实体
 *
 * @author gklx
 */
@Data
@TableName("t_kb_segment")
public class KbSegmentEntity {

    /**
     * 分段ID
     */
    @TableId(type = IdType.AUTO)
    private Long segmentId;

    /**
     * 所属文档ID
     */
    private Long documentId;

    /**
     * 所属知识库ID(冗余,便于检索过滤)
     */
    private Long baseId;

    /**
     * 文档内序号
     */
    private Integer segmentIndex;

    /**
     * 分段文本内容
     */
    private String content;

    /**
     * 内容长度
     */
    private Integer characterCount;

    /**
     * Weaviate 对象ID
     */
    private String vectorId;

    /**
     * 状态:0-未向量化 1-已向量化 2-向量化失败
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
