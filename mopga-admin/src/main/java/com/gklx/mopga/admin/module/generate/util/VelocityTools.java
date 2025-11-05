package com.gklx.mopga.admin.module.generate.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Collection;

public class VelocityTools {


    public boolean StringIsEmpty(String string) {
        return StrUtil.isEmpty(string);
    }

    public boolean StringIsNotEmpty(String string) {
        return StrUtil.isNotEmpty(string);
    }

    public boolean CollectionIsEmpty(Collection<?> collection) {
        return CollectionUtil.isEmpty(collection);
    }

    public boolean CollectionIsNotEmpty(Collection<?> collection) {
        return CollectionUtil.isNotEmpty(collection);
    }
}
