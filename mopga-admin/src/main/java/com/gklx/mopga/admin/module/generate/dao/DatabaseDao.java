package com.gklx.mopga.admin.module.generate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.domain.entity.DatabaseEntity;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseQueryForm;
import com.gklx.mopga.admin.module.generate.domain.vo.DatabaseVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据源表 Dao
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Mapper
public interface DatabaseDao extends BaseMapper<DatabaseEntity> {
    
    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<DatabaseVo> queryPage(Page page, @Param("queryForm") DatabaseQueryForm queryForm);

}