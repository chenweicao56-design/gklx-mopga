package com.gklx.mopga.admin.module.generate.domain.vo;

import com.gklx.mopga.admin.module.generate.domain.entity.TemplateCodeItemEntity;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateColumnEntity;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateMappingItemEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * $模板表 列表VO
 *
 * @Author ${.backendAuthor}
 * @Date 2025-09-18 17:17:12
 * @Copyright gklx
 */

@Data
public class TemplateVO {


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

    @Schema(description = "模板名称")
    private String templateName;

    @Schema(description = "模板类型")
    private String templateType;

    @Schema(description = "数据源类型")
    private String databaseType;

    @Schema(description = "语言类型")
    private String languageType;

    @Schema(description = "模板文件")
    private String templateFiles;

    @Schema(description = "项目路径")
    private String projectPath;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "模板代码项")
    private List<TemplateCodeItemEntity> templateCodeItems;

    @Schema(description = "模板字段项")
    private List<TemplateColumnEntity> templateColumns;

    @Schema(description = "模板映射项")
    private List<TemplateMappingItemEntity> templateMappingItems;


}
