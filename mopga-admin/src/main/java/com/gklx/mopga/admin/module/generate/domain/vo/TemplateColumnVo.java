package com.gklx.mopga.admin.module.generate.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * $模板字段 列表VO
 *
 * @Author ${.backendAuthor}
 * @Date 2025-09-26 15:45:20
 * @Copyright gklx
 */

@Data
public class TemplateColumnVo {


    @Schema(description = "ID")
    private Long columnId;

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

    @Schema(description = "模板id")
    private Long templateId;

    @Schema(description = "字段名称")
    private String columnName;

    @Schema(description = "字段注释")
    private String columnComment;

    @Schema(description = "是否主键（1是）")
    private Boolean isPk;

    @Schema(description = "是否自增（1是）")
    private Boolean isIncrement;

    @Schema(description = "是否为空（1是）")
    private Boolean isNull;

    @Schema(description = "默认值")
    private String columnDefault;

    @Schema(description = "字段类型")
    private String columnType;

    @Schema(description = "排序")
    private Integer sort;

    /**
     * 是否基类字段
     */
    private Boolean isBase;

}
