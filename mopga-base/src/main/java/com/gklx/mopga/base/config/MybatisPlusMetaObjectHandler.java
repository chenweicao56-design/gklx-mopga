package com.gklx.mopga.base.config;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis Plus 元对象处理器
 * 用于自动填充创建人、创建时间、更新人、更新时间
 *
 * @Author gklx
 * @Date 2026-06-12
 * @Copyright 1.0
 */
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 自动填充创建时间
        if (fieldExists("createTime", metaObject)) {
            setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }
        // 自动填充更新时间
        if (fieldExists("updateTime", metaObject)) {
            setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
        // 自动填充创建人，从 Sa-Token 获取当前登录用户
        if (fieldExists("createBy", metaObject)) {
            try {
                String userId = StpUtil.getLoginIdAsString();
                setFieldValByName("createBy", userId, metaObject);
            } catch (Exception e) {
                // 如果未登录，设置为 null
                setFieldValByName("createBy", null, metaObject);
            }
        }
        // 自动填充更新人，从 Sa-Token 获取当前登录用户
        if (fieldExists("updateBy", metaObject)) {
            try {
                String userId = StpUtil.getLoginIdAsString();
                setFieldValByName("updateBy", userId, metaObject);
            } catch (Exception e) {
                // 如果未登录，设置为 null
                setFieldValByName("updateBy", null, metaObject);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 自动填充更新时间
        if (fieldExists("updateTime", metaObject)) {
            setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
        // 自动填充更新人，从 Sa-Token 获取当前登录用户
        if (fieldExists("updateBy", metaObject)) {
            try {
                String userId = StpUtil.getLoginIdAsString();
                setFieldValByName("updateBy", userId, metaObject);
            } catch (Exception e) {
                // 如果未登录，设置为 null
                setFieldValByName("updateBy", null, metaObject);
            }
        }
    }

    /**
     * 判断字段是否存在
     */
    private boolean fieldExists(String fieldName, MetaObject metaObject) {
        return metaObject.hasSetter(fieldName);
    }
}
