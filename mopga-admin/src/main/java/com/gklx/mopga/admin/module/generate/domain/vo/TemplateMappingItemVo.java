package com.gklx.mopga.admin.module.generate.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * $字段类型映射表 列表VO
 *
 * @Author ${.backendAuthor}
 * @Date 2025-09-06 18:37:05
 * @Copyright gklx
 */

@Data
public class TemplateMappingItemVo {

    @Schema(description = "ID")
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

    @Schema(description = "模板id")
    private Long templateId;

    @Schema(description = "数据源字段类型")
    private String databaseColumnType;

    @Schema(description = "后端字段类型")
    private String backColumnType;

    @Schema(description = "前端字段类型")
    private String frontColumnType;

    @Schema(description = "前端组件")
    private String frontComponent;

    @Schema(description = "排序（越大越靠前）")
    private Integer sort;

}
