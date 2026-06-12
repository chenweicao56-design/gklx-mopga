package com.gklx.mopga.admin.module.generate.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * $文档标签关联表 列表VO
 *
 * @Author gklx
 * @Date 2026-06-12 09:54:56
 * @Copyright 1.0
 */

@Data
public class TDocumentTagRelationVo {

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "文档ID")
    private Long documentId;

    @Schema(description = "标签ID")
    private Long tagId;
}