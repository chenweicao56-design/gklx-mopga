package com.gklx.mopga.admin.module.generate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.domain.entity.MappingEntity;
import com.gklx.mopga.admin.module.generate.domain.form.MappingQueryForm;
import com.gklx.mopga.admin.module.generate.domain.vo.MappingVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射表 Dao
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Mapper
public interface MappingDao extends BaseMapper<MappingEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<MappingVo> queryPage(Page page, @Param("queryForm") MappingQueryForm queryForm);

}
