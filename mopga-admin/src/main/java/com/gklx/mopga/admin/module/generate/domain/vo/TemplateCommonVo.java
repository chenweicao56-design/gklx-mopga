package com.gklx.mopga.admin.module.generate.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * $公共模板 列表VO
 *
 * @Author gklx
 * @Date 2026-05-12 09:44:02
 * @Copyright 1.0
 */

@Data
public class TemplateCommonVo {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "创建人")
    private Long createUserId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新人")
    private Long updateUserId;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "删除状态")
    private Boolean deletedFlag;

    @Schema(description = "模板名称")
    private String templateName;

    @Schema(description = "模板内容")
    private String content;

    @Schema(description = "文件类型")
    private String fileType;

    @Schema(description = "类别")
    private String category;

    @Schema(description = "排序")
    private Integer sort;
}