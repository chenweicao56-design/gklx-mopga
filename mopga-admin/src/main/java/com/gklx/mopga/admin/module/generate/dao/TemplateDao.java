package com.gklx.mopga.admin.module.generate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateQueryForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TemplateVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模板表 Dao
 *
 * @Author gklx
 * @Date 2025-09-18 16:47:49
 * @Copyright gklx
 */

@Mapper
public interface TemplateDao extends BaseMapper<TemplateEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<TemplateVO> queryPage(Page page, @Param("queryForm") TemplateQueryForm queryForm);

}
