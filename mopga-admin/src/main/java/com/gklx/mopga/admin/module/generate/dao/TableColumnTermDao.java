package com.gklx.mopga.admin.module.generate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.domain.entity.TableColumnTermEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TableColumnTermQueryForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TableColumnTermVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表字段术语 Dao
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Mapper
public interface TableColumnTermDao extends BaseMapper<TableColumnTermEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<TableColumnTermVo> queryPage(Page page, @Param("queryForm") TableColumnTermQueryForm queryForm);

}