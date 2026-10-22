package com.gklx.mopga.admin.module.ai.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.ai.domain.entity.ReqReqEntity;
import com.gklx.mopga.admin.module.ai.domain.form.ReqReqQueryForm;
import com.gklx.mopga.admin.module.ai.domain.vo.ReqReqVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 需求表 Dao
 *
 * @Author gklx
 * @Date 2026-10-19 14:28:17
 * @Copyright 1.0
 */

@Mapper
public interface ReqReqDao extends BaseMapper<ReqReqEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<ReqReqVo> queryPage(Page page, @Param("queryForm") ReqReqQueryForm queryForm);
  
}