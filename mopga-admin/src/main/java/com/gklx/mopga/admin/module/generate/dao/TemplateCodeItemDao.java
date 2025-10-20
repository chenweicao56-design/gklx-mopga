package com.gklx.mopga.admin.module.generate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateCodeItemEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateCodeItemQueryForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TemplateCodeItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 代码模板项表 Dao
 *
 * @Author gklx
 * @Date 2025-09-18 17:05:30
 * @Copyright gklx
 */

@Mapper
public interface TemplateCodeItemDao extends BaseMapper<TemplateCodeItemEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<TemplateCodeItemVo> queryPage(Page page, @Param("queryForm") TemplateCodeItemQueryForm queryForm);

}
