package com.gklx.mopga.admin.module.generate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.domain.entity.LogTemplateCodeEntity;
import com.gklx.mopga.admin.module.generate.domain.form.LogTemplateCodeQueryForm;
import com.gklx.mopga.admin.module.generate.domain.vo.LogTemplateCodeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模版代码日志表 Dao
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Mapper
public interface LogTemplateCodeDao extends BaseMapper<LogTemplateCodeEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<LogTemplateCodeVo> queryPage(Page page, @Param("queryForm") LogTemplateCodeQueryForm queryForm);

}