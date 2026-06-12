package com.gklx.mopga.admin.module.generate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.domain.entity.TDocumentVersionEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentVersionQueryForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TDocumentVersionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文档版本历史表 Dao
 *
 * @Author gklx
 * @Date 2026-06-12 09:55:03
 * @Copyright 1.0
 */

@Mapper
public interface TDocumentVersionDao extends BaseMapper<TDocumentVersionEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<TDocumentVersionVo> queryPage(Page page, @Param("queryForm") TDocumentVersionQueryForm queryForm);

}