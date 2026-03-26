package com.gklx.mopga.admin.module.generate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.domain.entity.PromptTemplateEntity;
import com.gklx.mopga.admin.module.generate.domain.form.PromptTemplateQueryForm;
import com.gklx.mopga.admin.module.generate.domain.vo.PromptTemplateVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 提示词模版 Dao
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Mapper
public interface PromptTemplateDao extends BaseMapper<PromptTemplateEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<PromptTemplateVo> queryPage(Page page, @Param("queryForm") PromptTemplateQueryForm queryForm);

}