package com.gklx.mopga.admin.module.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 数据源表 实体类
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Data
@TableName("gen_database")
public class DatabaseEntity {

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
     * 名称
     */
    private String databaseName;

    /**
     * 类型
     */
    private String databaseType;

    /**
     * 语言类型
     */
    private String languageType;

    /**
     * 状态
     */
    private String databaseStatus;

    /**
     * 业务分类id
     */
    private Integer categoryId;

    /**
     * 数据源地址
     */
    private String url;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 超时时间
     */
    private Integer timeout;

    /**
     * 后端作者
     */
    private String backendAuthor;

    /**
     * 后端日期
     */
    private LocalDateTime backendDate;

    /**
     * 后端项目路径
     */
    private String backendProjectPath;

    /**
     * 前端作者
     */
    private String frontAuthor;

    /**
     * 前端日期
     */
    private LocalDateTime frontDate;

    /**
     * 前端项目路径
     */
    private String frontProjectPath;

    /**
     * 版权
     */
    private String copyright;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 表扩展字段
     */
    private String tableExtendedData;

    /**
     * 字段扩展字段
     */
    private String columnExtendedData;

    /**
     * 逻辑删除
     */
    private Boolean isPhysicallyDeleted;

    /**
     * 模板id
     */
    private Long templateId;

    /**
     * 表前缀
     */
    private String tablePrefix;

    /**
     * 是否分页（1是）
     */
    private Boolean isPage;

    /**
     * 是否详情（1是）
     */
    private Boolean isDetail;

    /**
     * 是否增加（1是）
     */
    private Boolean isAdd;

    /**
     * 是否修改（1是）
     */
    private Boolean isUpdate;

    /**
     * 是否删除（1是）
     */
    private Boolean isDelete;

    /**
     * 是否批量删除（1是）
     */
    private Boolean isBatchDelete;

    /**
     * 编辑组件
     */
    private String editComponent;

    /**
     * 每行几个表单
     */
    private Integer formCountLine;
}