package com.gklx.mopga.admin.module.requirement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.requirement.domain.entity.RequirementStatusLogEntity;
import com.gklx.mopga.admin.module.requirement.domain.vo.RequirementStatusLogVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 需求管理-需求状态流转历史 Dao
 *
 * @author gklx
 */
@Mapper
public interface RequirementStatusLogDao extends BaseMapper<RequirementStatusLogEntity> {

    /**
     * 查询某需求的状态流转历史
     */
    List<RequirementStatusLogVO> queryByRequirementId(@Param("requirementId") Long requirementId);
}
