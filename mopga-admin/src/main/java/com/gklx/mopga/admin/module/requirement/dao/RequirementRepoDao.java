package com.gklx.mopga.admin.module.requirement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gklx.mopga.admin.module.requirement.domain.entity.RequirementRepoEntity;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementRepoQueryForm;
import com.gklx.mopga.admin.module.requirement.domain.vo.RequirementRepoVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 需求管理-工程(git仓库) Dao
 *
 * @author gklx
 */
@Mapper
public interface RequirementRepoDao extends BaseMapper<RequirementRepoEntity> {

    /**
     * 分页查询工程列表
     */
    List<RequirementRepoVO> queryPage(Page<?> page, @Param("queryForm") RequirementRepoQueryForm queryForm);

    /**
     * 查询工程详情
     */
    RequirementRepoVO getDetail(@Param("repoId") Long repoId);

    /**
     * 校验同项目下仓库名是否重复
     */
    RequirementRepoEntity queryByProjectAndName(@Param("projectId") Long projectId,
                                                @Param("repoName") String repoName,
                                                @Param("excludeRepoId") Long excludeRepoId);
}
