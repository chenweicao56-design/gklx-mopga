package com.gklx.mopga.admin.module.generate.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * $代码模板项表 列表VO
 *
 * @Author ${.backendAuthor}
 * @Date 2025-09-18 17:18:23
 * @Copyright gklx
 */

@Data
public class TemplateCodeItemVo {


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

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "文件类型")
    private String fileType;

    @Schema(description = "文件路径")
    private String filePath;

    @Schema(description = "模板内容")
    private String content;

    @Schema(description = "排序")
    private Integer sort;

}
