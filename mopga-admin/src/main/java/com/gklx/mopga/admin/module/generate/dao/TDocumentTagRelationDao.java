package com.gklx.mopga.admin.module.generate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.domain.entity.TDocumentTagRelationEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentTagRelationQueryForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TDocumentTagRelationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文档标签关联表 Dao
 *
 * @Author gklx
 * @Date 2026-06-12 09:54:56
 * @Copyright 1.0
 */

@Mapper
public interface TDocumentTagRelationDao extends BaseMapper<TDocumentTagRelationEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<TDocumentTagRelationVo> queryPage(Page page, @Param("queryForm") TDocumentTagRelationQueryForm queryForm);

}