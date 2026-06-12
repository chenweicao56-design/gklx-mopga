package com.gklx.mopga.admin.module.generate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.domain.entity.TDocumentTagEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentTagQueryForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TDocumentTagVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 标签表 Dao
 *
 * @Author gklx
 * @Date 2026-06-12 09:54:50
 * @Copyright 1.0
 */

@Mapper
public interface TDocumentTagDao extends BaseMapper<TDocumentTagEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<TDocumentTagVo> queryPage(Page page, @Param("queryForm") TDocumentTagQueryForm queryForm);

}