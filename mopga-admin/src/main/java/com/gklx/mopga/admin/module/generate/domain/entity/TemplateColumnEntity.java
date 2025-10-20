package com.gklx.mopga.admin.module.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 模板字段 实体类
 *
 * @Author gklx
 * @Date 2025-09-26 15:45:20
 * @Copyright gklx
 */

@Data
@TableName("gen_template_column")
public class TemplateColumnEntity {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long columnId;

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
     * 字段名称
     */
    private String columnName;

    /**
     * 字段注释
     */
    private String columnComment;

    /**
     * 是否主键（1是）
     */
    private Boolean isPk;

    /**
     * 是否自增（1是）
     */
    private Boolean isIncrement;

    /**
     * 是否为空（1是）
     */
    private Boolean isNull;

    /**
     * 默认值
     */
    private String columnDefault;

    /**
     * 字段类型
     */
    private String columnType;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段注释
     */
    private String fieldComment;

    /**
     * 字段类型
     */
    private String fieldType;

    /**
     * 前端类型
     */
    private String jsType;

    /**
     * 字典id
     */
    private Long dictId;

    /**
     * 是否必填
     */
    private Boolean isRequired;

    /**
     * 是否新增
     */
    private Boolean isInsert;

    /**
     * 是否修改
     */
    private Boolean isUpdate;

    /**
     * 前端组件
     */
    private String frontComponent;

    /**
     * 是否查询条件
     */
    private Boolean isWhere;

    /**
     * 查询类型
     */
    private String whereType;

    /**
     * 扩展字段
     */
    private String extendedData;

    /**
     * 是否基类字段
     */
    private Boolean isBase;

    /**
     * 枚举类型
     */
    private String enumType;

}
