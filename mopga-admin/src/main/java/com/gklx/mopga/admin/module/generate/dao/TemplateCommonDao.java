package com.gklx.mopga.admin.module.generate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateCommonEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateCommonQueryForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TemplateCommonVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公共模板 Dao
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Mapper
public interface TemplateCommonDao extends BaseMapper<TemplateCommonEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<TemplateCommonVo> queryPage(Page page, @Param("queryForm") TemplateCommonQueryForm queryForm);

}