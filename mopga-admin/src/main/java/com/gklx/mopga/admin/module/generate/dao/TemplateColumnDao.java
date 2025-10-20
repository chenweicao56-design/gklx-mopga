package com.gklx.mopga.admin.module.generate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateColumnEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateColumnQueryForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TemplateColumnVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模板字段 Dao
 *
 * @Author gklx
 * @Date 2025-09-26 15:45:20
 * @Copyright gklx
 */

@Mapper
public interface TemplateColumnDao extends BaseMapper<TemplateColumnEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<TemplateColumnVo> queryPage(Page page, @Param("queryForm") TemplateColumnQueryForm queryForm);

}
