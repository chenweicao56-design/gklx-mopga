package com.gklx.mopga.admin.module.generate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateMappingItemEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateMappingItemQueryForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TemplateMappingItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字段类型映射表 Dao
 *
 * @Author gklx
 * @Date 2025-09-18 17:18:43
 * @Copyright gklx
 */

@Mapper
public interface TemplateMappingItemDao extends BaseMapper<TemplateMappingItemEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<TemplateMappingItemVo> queryPage(Page page, @Param("queryForm") TemplateMappingItemQueryForm queryForm);

}
