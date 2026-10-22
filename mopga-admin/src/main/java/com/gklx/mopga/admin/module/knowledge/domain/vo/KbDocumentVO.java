package com.gklx.mopga.admin.module.knowledge.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 知识库-文档VO
 *
 * @author gklx
 */
@Data
public class KbDocumentVO {

    @Schema(description = "文档ID")
    private Long documentId;

    @Schema(description = "知识库ID")
    private Long baseId;

    @Schema(description = "知识库名称")
    private String baseName;

    @Schema(description = "原始文件名")
    private String fileName;

    @Schema(description = "文件存储路径")
    private String fileUrl;

    @Schema(description = "文件字节数")
    private Long fileSize;

    @Schema(description = "文件大小(人类可读)")
    private String fileSizeDesc;

    @Schema(description = "扩展名")
    private String fileType;

    @Schema(description = "状态:0-上传中 1-待解析 2-解析中 3-已解析 4-分段中 5-向量化中 6-已生效 7-解析失败 8-向量化失败")
    private Integer status;

    @Schema(description = "状态描述")
    private String statusDesc;

    @Schema(description = "分段数量")
    private Integer chunkCount;

    @Schema(description = "失败原因")
    private String errorMessage;

    @Schema(description = "创建人名称")
    private String createUserName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
