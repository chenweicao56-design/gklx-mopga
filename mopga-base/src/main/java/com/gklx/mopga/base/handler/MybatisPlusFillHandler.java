package com.gklx.mopga.base.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.gklx.mopga.base.common.util.SmartRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Mybatis Plus 插入或者更新时指定字段设置值
 *
 * @author zhoumingfa
 */
@Component
@Slf4j
public class MybatisPlusFillHandler implements MetaObjectHandler {

    public static final String CREATE_TIME = "createTime";
    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_TIME = "updateTime";
    public static final String UPDATE_USER_ID = "updateUserId";

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasSetter(CREATE_TIME)) {
            this.fillStrategy(metaObject, CREATE_TIME, LocalDateTime.now());
        }
        if (metaObject.hasSetter(CREATE_USER_ID)) {
            this.fillStrategy(metaObject, CREATE_USER_ID, SmartRequestUtil.getRequestUserId());
        }
        if (metaObject.hasSetter(UPDATE_TIME)) {
            this.fillStrategy(metaObject, UPDATE_TIME, LocalDateTime.now());
        }
        if (metaObject.hasSetter(UPDATE_USER_ID)) {
            this.fillStrategy(metaObject, UPDATE_USER_ID, SmartRequestUtil.getRequestUserId());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter(UPDATE_TIME)) {
            this.fillStrategy(metaObject, UPDATE_TIME, LocalDateTime.now());
        }
        if (metaObject.hasSetter(UPDATE_USER_ID)) {
            this.fillStrategy(metaObject, UPDATE_USER_ID, SmartRequestUtil.getRequestUserId());
        }
    }

}
