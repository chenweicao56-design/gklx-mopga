package com.gklx.mopga.admin.module.knowledge.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 知识库-分段VO
 *
 * @author gklx
 */
@Data
public class KbSegmentVO {

    @Schema(description = "分段ID")
    private Long segmentId;

    @Schema(description = "文档ID")
    private Long documentId;

    @Schema(description = "知识库ID")
    private Long baseId;

    @Schema(description = "文档内序号")
    private Integer segmentIndex;

    @Schema(description = "分段文本内容")
    private String content;

    @Schema(description = "内容长度")
    private Integer characterCount;

    @Schema(description = "Weaviate 对象ID")
    private String vectorId;

    @Schema(description = "状态:0-未向量化 1-已向量化 2-向量化失败")
    private Integer status;

    @Schema(description = "状态描述")
    private String statusDesc;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
