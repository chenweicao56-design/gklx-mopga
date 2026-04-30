package com.gklx.mopga.admin.module.generate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.domain.entity.DatabaseTermEntity;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseTermQueryForm;
import com.gklx.mopga.admin.module.generate.domain.vo.DatabaseTermVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据源术语表 Dao
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Mapper
public interface DatabaseTermDao extends BaseMapper<DatabaseTermEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<DatabaseTermVo> queryPage(Page page, @Param("queryForm") DatabaseTermQueryForm queryForm);

}