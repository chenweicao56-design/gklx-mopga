package com.gklx.mopga.admin.module.ai.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.ai.domain.entity.SkillEntity;
import com.gklx.mopga.admin.module.ai.domain.form.SkillQueryForm;
import com.gklx.mopga.admin.module.ai.domain.vo.SkillVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 技能主表 Dao
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:22
 * @Copyright 1.0
 */

@Mapper
public interface SkillDao extends BaseMapper<SkillEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<SkillVo> queryPage(Page page, @Param("queryForm") SkillQueryForm queryForm);

}