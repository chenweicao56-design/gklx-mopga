package com.gklx.mopga.admin.module.generate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.domain.entity.TDocumentEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentQueryForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TDocumentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文档主表 Dao
 *
 * @Author gklx
 * @Date 2026-06-12 09:54:33
 * @Copyright 1.0
 */

@Mapper
public interface TDocumentDao extends BaseMapper<TDocumentEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<TDocumentVo> queryPage(Page page, @Param("queryForm") TDocumentQueryForm queryForm);

}