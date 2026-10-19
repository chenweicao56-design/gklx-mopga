package com.gklx.mopga.admin.module.ai.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.ai.domain.entity.ReqRepoEntity;
import com.gklx.mopga.admin.module.ai.domain.form.ReqRepoQueryForm;
import com.gklx.mopga.admin.module.ai.domain.vo.ReqRepoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工程（代码仓库）表 Dao
 *
 * @Author gklx
 * @Date 2026-10-19 14:28:17
 * @Copyright 1.0
 */

@Mapper
public interface ReqRepoDao extends BaseMapper<ReqRepoEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<ReqRepoVo> queryPage(Page page, @Param("queryForm") ReqRepoQueryForm queryForm);
  
}