package com.gklx.mopga.admin.module.requirement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.requirement.domain.entity.RequirementEntity;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementQueryForm;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementListQueryForm;
import com.gklx.mopga.admin.module.requirement.domain.vo.RequirementVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 需求管理-需求 Dao
 *
 * @author gklx
 */
@Mapper
public interface RequirementDao extends BaseMapper<RequirementEntity> {

    /**
     * 分页查询需求列表
     */
    List<RequirementVO> queryPage(Page<?> page, @Param("queryForm") RequirementQueryForm queryForm);

    /**
     * 查询需求详情(含关联工程)
     */
    RequirementVO getDetail(@Param("requirementId") Long requirementId);

    /**
     * 按编号查询需求详情(用于MCP)
     */
    RequirementVO getByNumber(@Param("reqNumber") String reqNumber);

    /**
     * MCP 列表查询
     */
    List<RequirementVO> queryList(@Param("queryForm") RequirementListQueryForm queryForm);

    /**
     * 统计某个项目下需求数量(删除项目校验用)
     */
    Long countByProjectId(@Param("projectId") Long projectId);
}
