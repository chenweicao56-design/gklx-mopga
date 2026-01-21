package com.gklx.mopga.admin.module.generate.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Collection;
import java.util.regex.Pattern;

public class VelocityTools {

    private static final Pattern BRACKET_PATTERN = Pattern.compile("(\\([^)]*\\)|（[^）]*）)");

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

    public String FormatComment(String comment) {
        return BRACKET_PATTERN.matcher(comment).replaceAll("");
    }

    public String WordName(String value) {
        return StrUtil.upperFirst(value);
    }

    public String word_name(String value) {
        return StrUtil.toUnderlineCase(value);
    }

    public String wordname(String value) {
        return StrUtil.toUnderlineCase(value).replace("_", "-");
    }
}
