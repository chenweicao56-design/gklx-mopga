package com.gklx.mopga.base.mapper;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SqlMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    // 自定义通用方法
    int myUpdate(@Param("sql") String sql);

    List<Map<String, Object>> mySelect(@Param("sql") String sql);

    void executeSql(@Param("sql") String sql);
}