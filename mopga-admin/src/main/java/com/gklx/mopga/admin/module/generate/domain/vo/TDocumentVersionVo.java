package com.gklx.mopga.admin.module.generate.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * $文档版本历史表 列表VO
 *
 * @Author gklx
 * @Date 2026-06-12 09:55:03
 * @Copyright 1.0
 */

@Data
public class TDocumentVersionVo {

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "文档ID")
    private Long documentId;

    @Schema(description = "版本号")
    private String version;

    @Schema(description = "文档名称")
    private String documentName;

    @Schema(description = "文档内容")
    private String documentContent;

    @Schema(description = "文件类型")
    private String fileType;

    @Schema(description = "文档类型")
    private String documentType;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}