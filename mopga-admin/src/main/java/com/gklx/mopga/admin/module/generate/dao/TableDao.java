package com.gklx.mopga.admin.module.generate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.domain.entity.TableEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TableQueryForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TableVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表 Dao
 *
 * @Author gklx
 * @Date 2025-09-19 13:31:14
 * @Copyright gklx
 */

@Mapper
public interface TableDao extends BaseMapper<TableEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<TableVO> queryPage(Page page, @Param("queryForm") TableQueryForm queryForm);

}
