package com.gklx.mopga.admin.module.generate.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * $模版代码日志表 列表VO
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
public class LogTemplateCodeVo {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "创建人")
    private Long createUserId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "模版代码主键")
    private Long templateCodeId;

    @Schema(description = "代码内容")
    private String content;

    @Schema(description = "类型（1：新增，2：修改，3：删除）")
    private String type;
}