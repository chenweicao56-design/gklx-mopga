package com.gklx.mopga.admin.module.ai.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.ai.domain.entity.ReqProjectEntity;
import com.gklx.mopga.admin.module.ai.domain.form.ReqProjectQueryForm;
import com.gklx.mopga.admin.module.ai.domain.vo.ReqProjectVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目表 Dao
 *
 * @Author gklx
 * @Date 2026-10-19 14:28:17
 * @Copyright 1.0
 */

@Mapper
public interface ReqProjectDao extends BaseMapper<ReqProjectEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<ReqProjectVo> queryPage(Page page, @Param("queryForm") ReqProjectQueryForm queryForm);
  
}