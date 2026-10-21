package com.gklx.mopga.admin.module.requirement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gklx.mopga.admin.module.requirement.domain.entity.RequirementRepoRelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 需求管理-需求工程关联 Dao
 *
 * @author gklx
 */
@Mapper
public interface RequirementRepoRelDao extends BaseMapper<RequirementRepoRelEntity> {

    /**
     * 查询某需求关联的工程ID集合
     */
    List<Long> queryRepoIdList(@Param("requirementId") Long requirementId);

    /**
     * 查询某工程被多少个需求关联(删除工程校验用)
     */
    Long countByRepoId(@Param("repoId") Long repoId);

    /**
     * 批量删除某需求的关联
     */
    void deleteByRequirementId(@Param("requirementId") Long requirementId);
}
