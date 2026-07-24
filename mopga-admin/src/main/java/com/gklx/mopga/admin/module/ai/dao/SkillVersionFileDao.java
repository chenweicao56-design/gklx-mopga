package com.gklx.mopga.admin.module.ai.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.ai.domain.entity.SkillVersionFileEntity;
import com.gklx.mopga.admin.module.ai.domain.form.SkillVersionFileQueryForm;
import com.gklx.mopga.admin.module.ai.domain.vo.SkillVersionFileVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 技能版本文件快照表 Dao
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:24
 * @Copyright 1.0
 */

@Mapper
public interface SkillVersionFileDao extends BaseMapper<SkillVersionFileEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<SkillVersionFileVo> queryPage(Page page, @Param("queryForm") SkillVersionFileQueryForm queryForm);

}