package com.gklx.mopga.admin.module.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 模板 实体类
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
@TableName("gen_table_column")
public class GenTableColumnEntity {

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
     * 表id
     */
    private Long tableId;

    /**
     * 数据源id
     */
    private Long databaseId;

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
     * 字典类型
     */
    private String dictType;

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

    /**
     * 是否表格
     */
    private Boolean isTable;
}