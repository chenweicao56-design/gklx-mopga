package com.gklx.mopga.admin.module.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 字段类型映射表 实体类
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright gklx
 */

@Data
@TableName("gen_template_mapping_item")
public class TemplateMappingItemEntity {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 删除状态
     */
    private Boolean deletedFlag;

    /**
     * 模板id
     */
    private Long templateId;

    /**
     * 数据源字段类型
     */
    private String databaseColumnType;

    /**
     * 后端字段类型
     */
    private String backColumnType;

    /**
     * 前端字段类型
     */
    private String frontColumnType;

    /**
     * 前端组件
     */
    private String frontComponent;

    /**
     * 排序（越大越靠前）
     */
    private Integer sort;
}
