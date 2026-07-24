package com.gklx.mopga.admin.module.ai.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.ai.domain.entity.SkillFileEntity;
import com.gklx.mopga.admin.module.ai.domain.form.SkillFileQueryForm;
import com.gklx.mopga.admin.module.ai.domain.vo.SkillFileVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 技能草稿文件表 Dao
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:23
 * @Copyright 1.0
 */

@Mapper
public interface SkillFileDao extends BaseMapper<SkillFileEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<SkillFileVo> queryPage(Page page, @Param("queryForm") SkillFileQueryForm queryForm);

}