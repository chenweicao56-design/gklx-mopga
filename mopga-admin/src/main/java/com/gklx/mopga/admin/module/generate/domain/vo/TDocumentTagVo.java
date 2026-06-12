package com.gklx.mopga.admin.module.generate.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * $标签表 列表VO
 *
 * @Author gklx
 * @Date 2026-06-12 09:54:50
 * @Copyright 1.0
 */

@Data
public class TDocumentTagVo {

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "标签名称")
    private String tagName;

    @Schema(description = "是否系统标签(0否/1是)")
    private Integer isSystem;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}