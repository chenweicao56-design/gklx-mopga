package com.gklx.mopga.admin.module.generate.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * $映射表 列表VO
 *
 * @Author ${.backendAuthor}
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
public class MappingVo {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "映射名称")
    private String name;

    @Schema(description = "映射编码")
    private String code;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "排序")
    private Integer sort;

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

}
