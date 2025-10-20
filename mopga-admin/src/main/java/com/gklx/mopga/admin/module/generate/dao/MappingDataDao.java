package com.gklx.mopga.admin.module.generate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.domain.entity.MappingDataEntity;
import com.gklx.mopga.admin.module.generate.domain.form.MappingDataQueryForm;
import com.gklx.mopga.admin.module.generate.domain.vo.MappingDataVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射数据表 Dao
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Mapper
public interface MappingDataDao extends BaseMapper<MappingDataEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<MappingDataVo> queryPage(Page page, @Param("queryForm") MappingDataQueryForm queryForm);

}
