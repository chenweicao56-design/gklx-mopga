package com.gklx.mopga.admin.module.generate.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * $文档主表 列表VO
 *
 * @Author gklx
 * @Date 2026-06-12 09:54:33
 * @Copyright 1.0
 */

@Data
public class TDocumentVo {

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "文档名称")
    private String documentName;

    @Schema(description = "文档内容")
    private String documentContent;

    @Schema(description = "文件类型(markdown/json/text/code等)")
    private String fileType;

    @Schema(description = "文档类型(技术文档/用户手册/配置文件等)")
    private String documentType;

    @Schema(description = "版本号")
    private String version;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新人")
    private String updateBy;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}