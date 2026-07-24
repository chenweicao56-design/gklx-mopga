package com.gklx.mopga.admin.module.ai.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.ai.domain.entity.SkillVersionEntity;
import com.gklx.mopga.admin.module.ai.domain.form.SkillVersionQueryForm;
import com.gklx.mopga.admin.module.ai.domain.vo.SkillVersionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 技能版本表 Dao
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:24
 * @Copyright 1.0
 */

@Mapper
public interface SkillVersionDao extends BaseMapper<SkillVersionEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<SkillVersionVo> queryPage(Page page, @Param("queryForm") SkillVersionQueryForm queryForm);

}