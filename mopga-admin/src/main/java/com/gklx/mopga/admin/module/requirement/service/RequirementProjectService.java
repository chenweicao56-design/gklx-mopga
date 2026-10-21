package com.gklx.mopga.admin.module.requirement.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.requirement.dao.RequirementProjectDao;
import com.gklx.mopga.admin.module.requirement.dao.RequirementRepoDao;
import com.gklx.mopga.admin.module.requirement.dao.RequirementDao;
import com.gklx.mopga.admin.module.requirement.dao.RequirementRepoRelDao;
import com.gklx.mopga.admin.module.requirement.domain.entity.RequirementProjectEntity;
import com.gklx.mopga.admin.module.requirement.domain.entity.RequirementRepoEntity;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementProjectAddForm;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementProjectQueryForm;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementProjectUpdateForm;
import com.gklx.mopga.admin.module.requirement.domain.vo.RequirementProjectVO;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
import com.gklx.mopga.base.common.util.SmartPageUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 需求管理-项目 Service
 *
 * @author gklx
 */
@Slf4j
@Service
public class RequirementProjectService {

    @Resource
    private RequirementProjectDao projectDao;

    @Resource
    private RequirementRepoDao repoDao;

    @Resource
    private RequirementDao requirementDao;

    @Resource
    private RequirementRepoRelDao repoRelDao;

    /**
     * 分页查询项目列表
     */
    public ResponseDTO<PageResult<RequirementProjectVO>> queryPage(RequirementProjectQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        LambdaQueryWrapper<RequirementProjectEntity> wrapper = new LambdaQueryWrapper<RequirementProjectEntity>()
                .like(StringUtils.isNotBlank(queryForm.getProjectName()), RequirementProjectEntity::getProjectName, queryForm.getProjectName())
                .like(StringUtils.isNotBlank(queryForm.getProjectCode()), RequirementProjectEntity::getProjectCode, queryForm.getProjectCode())
                .eq(queryForm.getStatus() != null, RequirementProjectEntity::getStatus, queryForm.getStatus())
                .orderByDesc(RequirementProjectEntity::getCreateTime);
        Page<RequirementProjectEntity> entityPage = projectDao.selectPage((Page<RequirementProjectEntity>) page, wrapper);
        List<RequirementProjectEntity> entityList = entityPage.getRecords();
        List<RequirementProjectVO> voList = entityList.stream().map(this::convert2VO).toList();
        PageResult<RequirementProjectVO> pageResult = SmartPageUtil.convert2PageResult(page, voList);
        return ResponseDTO.ok(pageResult);
    }

    @Transactional(readOnly = true)
    public ResponseDTO<RequirementProjectVO> getDetail(Long projectId) {
        RequirementProjectEntity entity = projectDao.selectById(projectId);
        if (entity == null) {
            return ResponseDTO.userErrorParam("项目不存在");
        }
        return ResponseDTO.ok(convert2VO(entity));
    }

    /**
     * 查询全部启用项目(ID列表用于下拉)
     */
    public ResponseDTO<List<RequirementProjectVO>> queryEnabledList() {
        List<RequirementProjectEntity> entityList = projectDao.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<RequirementProjectEntity>()
                        .eq(RequirementProjectEntity::getStatus, 0)
                        .orderByDesc(RequirementProjectEntity::getCreateTime));
        List<RequirementProjectVO> voList = entityList.stream().map(this::convert2VO).toList();
        return ResponseDTO.ok(voList);
    }

    /**
     * 新建项目
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> create(RequirementProjectAddForm addForm) {
        // 校验项目编码唯一
        RequirementProjectEntity exist = projectDao.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<RequirementProjectEntity>()
                        .eq(RequirementProjectEntity::getProjectCode, addForm.getProjectCode())
                        .last("LIMIT 1"));
        if (exist != null) {
            return ResponseDTO.userErrorParam("项目编码已存在");
        }
        RequirementProjectEntity entity = SmartBeanUtil.copy(addForm, RequirementProjectEntity.class);
        entity.setStatus(0);
        projectDao.insert(entity);
        return ResponseDTO.ok();
    }

    /**
     * 编辑项目
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(RequirementProjectUpdateForm updateForm) {
        RequirementProjectEntity entity = projectDao.selectById(updateForm.getProjectId());
        if (entity == null) {
            return ResponseDTO.userErrorParam("项目不存在");
        }
        // 项目编码创建后不可改(作为编号前缀)
        entity.setProjectName(updateForm.getProjectName());
        entity.setDescription(updateForm.getDescription());
        entity.setOwner(updateForm.getOwner());
        entity.setStatus(updateForm.getStatus());
        projectDao.updateById(entity);
        return ResponseDTO.ok();
    }

    /**
     * 删除项目(存在工程/需求时禁止删除)
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long projectId) {
        RequirementProjectEntity entity = projectDao.selectById(projectId);
        if (entity == null) {
            return ResponseDTO.userErrorParam("项目不存在");
        }
        // 校验是否有工程
        Long repoCount = repoDao.selectCount(
                new LambdaQueryWrapper<RequirementRepoEntity>()
                        .eq(RequirementRepoEntity::getProjectId, projectId));        if (repoCount != null && repoCount > 0) {
            return ResponseDTO.userErrorParam("请先删除该项目下的工程");
        }
        // 校验是否有需求
        Long reqCount = requirementDao.countByProjectId(projectId);
        if (reqCount != null && reqCount > 0) {
            return ResponseDTO.userErrorParam("请先删除该项目下的需求");
        }
        projectDao.deleteById(projectId);
        return ResponseDTO.ok();
    }

    /**
     * 实体转VO并填充状态描述、工程/需求数量
     */
    private RequirementProjectVO convert2VO(RequirementProjectEntity entity) {
        RequirementProjectVO vo = SmartBeanUtil.copy(entity, RequirementProjectVO.class);
        vo.setStatusDesc(Objects.equals(entity.getStatus(), 0) ? "启用" : "停用");
        Long repoCount = repoDao.selectCount(
                new LambdaQueryWrapper<RequirementRepoEntity>()
                        .eq(RequirementRepoEntity::getProjectId, entity.getProjectId()));
        vo.setRepoCount(repoCount == null ? 0L : repoCount);
        Long reqCount = requirementDao.countByProjectId(entity.getProjectId());
        vo.setRequirementCount(reqCount == null ? 0L : reqCount);
        return vo;
    }
}
