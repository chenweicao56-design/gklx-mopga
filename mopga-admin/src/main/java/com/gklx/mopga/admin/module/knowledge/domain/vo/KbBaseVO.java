package com.gklx.mopga.admin.module.knowledge.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 知识库-VO
 *
 * @author gklx
 */
@Data
public class KbBaseVO {

    @Schema(description = "知识库ID")
    private Long baseId;

    @Schema(description = "知识库名称")
    private String name;

    @Schema(description = "知识库描述")
    private String description;

    @Schema(description = "文档数量")
    private Integer docCount;

    @Schema(description = "创建人ID")
    private Long createUserId;

    @Schema(description = "创建人名称")
    private String createUserName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
