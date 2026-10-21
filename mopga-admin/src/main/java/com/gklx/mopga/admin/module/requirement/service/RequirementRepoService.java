package com.gklx.mopga.admin.module.requirement.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.requirement.dao.RequirementProjectDao;
import com.gklx.mopga.admin.module.requirement.dao.RequirementRepoDao;
import com.gklx.mopga.admin.module.requirement.dao.RequirementRepoRelDao;
import com.gklx.mopga.admin.module.requirement.domain.entity.RequirementProjectEntity;
import com.gklx.mopga.admin.module.requirement.domain.entity.RequirementRepoEntity;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementRepoAddForm;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementRepoQueryForm;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementRepoUpdateForm;
import com.gklx.mopga.admin.module.requirement.domain.vo.RequirementRepoVO;
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
 * 需求管理-工程(git仓库) Service
 *
 * @author gklx
 */
@Slf4j
@Service
public class RequirementRepoService {

    @Resource
    private RequirementRepoDao repoDao;

    @Resource
    private RequirementProjectDao projectDao;

    @Resource
    private RequirementRepoRelDao repoRelDao;

    /**
     * 分页查询工程列表
     */
    public ResponseDTO<PageResult<RequirementRepoVO>> queryPage(RequirementRepoQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<RequirementRepoVO> voList = repoDao.queryPage(page, queryForm);
        PageResult<RequirementRepoVO> pageResult = SmartPageUtil.convert2PageResult(page, voList);
        return ResponseDTO.ok(pageResult);
    }

    public ResponseDTO<RequirementRepoVO> getDetail(Long repoId) {
        RequirementRepoVO vo = repoDao.getDetail(repoId);
        if (vo == null) {
            return ResponseDTO.userErrorParam("工程不存在");
        }
        fillVO(vo);
        return ResponseDTO.ok(vo);
    }

    /**
     * 查询某项目下启用中的工程(用于需求关联选择)
     */
    public ResponseDTO<List<RequirementRepoVO>> queryEnabledList(Long projectId) {
        List<RequirementRepoEntity> entityList = repoDao.selectList(
                new LambdaQueryWrapper<RequirementRepoEntity>()
                        .eq(RequirementRepoEntity::getProjectId, projectId)
                        .eq(RequirementRepoEntity::getStatus, 0)
                        .orderByDesc(RequirementRepoEntity::getCreateTime));
        List<RequirementRepoVO> voList = entityList.stream().map(e -> {
            RequirementRepoVO vo = SmartBeanUtil.copy(e, RequirementRepoVO.class);
            vo.setStatusDesc("启用");
            return vo;
        }).toList();
        return ResponseDTO.ok(voList);
    }

    /**
     * 新建工程
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> create(RequirementRepoAddForm addForm) {
        // 归属项目校验
        RequirementProjectEntity project = projectDao.selectById(addForm.getProjectId());
        if (project == null) {
            return ResponseDTO.userErrorParam("归属项目不存在");
        }
        if (Objects.equals(project.getStatus(), 1)) {
            return ResponseDTO.userErrorParam("归属项目已停用，不允许新增工程");
        }
        // 同项目下工程名重复校验
        RequirementRepoEntity exist = repoDao.queryByProjectAndName(addForm.getProjectId(), addForm.getRepoName(), null);
        if (exist != null) {
            return ResponseDTO.userErrorParam("该项目下已存在同名工程");
        }
        RequirementRepoEntity entity = SmartBeanUtil.copy(addForm, RequirementRepoEntity.class);
        if (StringUtils.isBlank(entity.getDefaultBranch())) {
            entity.setDefaultBranch("master");
        }
        entity.setStatus(0);
        repoDao.insert(entity);
        return ResponseDTO.ok();
    }

    /**
     * 编辑工程
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(RequirementRepoUpdateForm updateForm) {
        RequirementRepoEntity entity = repoDao.selectById(updateForm.getRepoId());
        if (entity == null) {
            return ResponseDTO.userErrorParam("工程不存在");
        }
        RequirementProjectEntity project = projectDao.selectById(entity.getProjectId());
        if (project == null) {
            return ResponseDTO.userErrorParam("归属项目不存在");
        }
        // 同项目下工程名重复校验(排除自身)
        RequirementRepoEntity exist = repoDao.queryByProjectAndName(entity.getProjectId(), updateForm.getRepoName(), updateForm.getRepoId());
        if (exist != null) {
            return ResponseDTO.userErrorParam("该项目下已存在同名工程");
        }
        entity.setRepoName(updateForm.getRepoName());
        entity.setRepoUrl(updateForm.getRepoUrl());
        entity.setDefaultBranch(StringUtils.isBlank(updateForm.getDefaultBranch()) ? "master" : updateForm.getDefaultBranch());
        entity.setLocalPath(updateForm.getLocalPath());
        entity.setDescription(updateForm.getDescription());
        entity.setStatus(updateForm.getStatus());
        repoDao.updateById(entity);
        return ResponseDTO.ok();
    }

    /**
     * 删除工程(存在关联需求时禁止删除)
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long repoId) {
        RequirementRepoEntity entity = repoDao.selectById(repoId);
        if (entity == null) {
            return ResponseDTO.userErrorParam("工程不存在");
        }
        Long relCount = repoRelDao.countByRepoId(repoId);
        if (relCount != null && relCount > 0) {
            return ResponseDTO.userErrorParam("请先解除工程与需求的关联");
        }
        repoDao.deleteById(repoId);
        return ResponseDTO.ok();
    }

    /**
     * 填充VO的状态描述与关联需求数量
     */
    private void fillVO(RequirementRepoVO vo) {
        vo.setStatusDesc(Objects.equals(vo.getStatus(), 0) ? "启用" : "停用");
        Long relCount = repoRelDao.countByRepoId(vo.getRepoId());
        vo.setRequirementCount(relCount == null ? 0L : relCount);
    }
}
