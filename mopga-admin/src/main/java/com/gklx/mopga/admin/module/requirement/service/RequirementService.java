package com.gklx.mopga.admin.module.requirement.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.requirement.constant.RequirementStatusEnum;
import com.gklx.mopga.admin.module.requirement.constant.RequirementStatusSourceEnum;
import com.gklx.mopga.admin.module.requirement.dao.RequirementDao;
import com.gklx.mopga.admin.module.requirement.dao.RequirementProjectDao;
import com.gklx.mopga.admin.module.requirement.dao.RequirementRepoDao;
import com.gklx.mopga.admin.module.requirement.dao.RequirementRepoRelDao;
import com.gklx.mopga.admin.module.requirement.dao.RequirementStatusLogDao;
import com.gklx.mopga.admin.module.requirement.domain.entity.RequirementEntity;
import com.gklx.mopga.admin.module.requirement.domain.entity.RequirementProjectEntity;
import com.gklx.mopga.admin.module.requirement.domain.entity.RequirementRepoEntity;
import com.gklx.mopga.admin.module.requirement.domain.entity.RequirementRepoRelEntity;
import com.gklx.mopga.admin.module.requirement.domain.entity.RequirementStatusLogEntity;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementAcceptanceForm;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementAddForm;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementListQueryForm;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementQueryForm;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementStatusUpdateForm;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementUpdateForm;
import com.gklx.mopga.admin.module.requirement.domain.vo.RequirementAcceptanceVO;
import com.gklx.mopga.admin.module.requirement.domain.vo.RequirementRepoVO;
import com.gklx.mopga.admin.module.requirement.domain.vo.RequirementStatusLogVO;
import com.gklx.mopga.admin.module.requirement.domain.vo.RequirementVO;
import com.gklx.mopga.admin.module.requirement.manager.RequirementNumberManager;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
import com.gklx.mopga.base.common.util.SmartPageUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 需求管理-需求 Service
 *
 * @author gklx
 */
@Slf4j
@Service
public class RequirementService {

    @Resource
    private RequirementDao requirementDao;

    @Resource
    private RequirementProjectDao projectDao;

    @Resource
    private RequirementRepoDao repoDao;

    @Resource
    private RequirementRepoRelDao repoRelDao;

    @Resource
    private RequirementStatusLogDao statusLogDao;

    @Resource
    private RequirementNumberManager requirementNumberManager;

    /**
     * 分页查询需求列表
     */
    public ResponseDTO<PageResult<RequirementVO>> queryPage(RequirementQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<RequirementVO> voList = requirementDao.queryPage(page, queryForm);
        voList.forEach(this::fillListVO);
        PageResult<RequirementVO> pageResult = SmartPageUtil.convert2PageResult(page, voList);
        return ResponseDTO.ok(pageResult);
    }

    /**
     * 查询需求详情
     */
    public ResponseDTO<RequirementVO> getDetail(Long requirementId) {
        RequirementVO vo = requirementDao.getDetail(requirementId);
        if (vo == null) {
            return ResponseDTO.userErrorParam("需求不存在");
        }
        fillDetailVO(vo);
        return ResponseDTO.ok(vo);
    }

    /**
     * 按编号查询需求详情(供MCP)
     */
    public RequirementVO getByNumber(String reqNumber) {
        if (StringUtils.isBlank(reqNumber)) {
            return null;
        }
        RequirementVO vo = requirementDao.getByNumber(reqNumber.trim());
        if (vo == null) {
            return null;
        }
        fillDetailVO(vo);
        return vo;
    }

    /**
     * 新建需求
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> create(RequirementAddForm addForm) {
        // 归属项目校验
        RequirementProjectEntity project = projectDao.selectById(addForm.getProjectId());
        if (project == null) {
            return ResponseDTO.userErrorParam("请选择归属项目");
        }
        if (Objects.equals(project.getStatus(), 1)) {
            return ResponseDTO.userErrorParam("该项目已停用，不允许创建需求");
        }
        // 生成编号
        String reqNumber = requirementNumberManager.generateNumber(project.getProjectCode());
        RequirementEntity entity = new RequirementEntity();
        entity.setReqNumber(reqNumber);
        entity.setProjectId(addForm.getProjectId());
        entity.setTitle(addForm.getTitle());
        entity.setContent(addForm.getContent());
        entity.setAcceptanceCriteria(toJson(addForm.getAcceptanceCriteria()));
        entity.setStatus(RequirementStatusEnum.PENDING.getValue());
        entity.setCreateBy(addForm.getCreateBy());
        entity.setCreateByName(addForm.getCreateByName());
        requirementDao.insert(entity);
        // 关联工程
        saveRepoRel(entity.getRequirementId(), addForm.getProjectId(), addForm.getRepoIdList());
        // 记录初始状态历史
        insertStatusLog(entity.getRequirementId(), null, entity.getStatus(),
                RequirementStatusSourceEnum.PAGE.getValue(), addForm.getCreateBy(), addForm.getCreateByName(), "创建需求");
        return ResponseDTO.okMsg(reqNumber);
    }

    /**
     * 编辑需求
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(RequirementUpdateForm updateForm) {
        RequirementEntity entity = requirementDao.selectById(updateForm.getRequirementId());
        if (entity == null) {
            return ResponseDTO.userErrorParam("需求不存在");
        }
        // 需求编号、归属项目创建后不可改
        entity.setTitle(updateForm.getTitle());
        entity.setContent(updateForm.getContent());
        entity.setAcceptanceCriteria(toJson(updateForm.getAcceptanceCriteria()));
        requirementDao.updateById(entity);
        // 重设关联工程(需求归属项目不变)
        saveRepoRel(entity.getRequirementId(), entity.getProjectId(), updateForm.getRepoIdList());
        return ResponseDTO.ok();
    }

    /**
     * 删除需求(仅待开发可删)
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long requirementId) {
        RequirementEntity entity = requirementDao.selectById(requirementId);
        if (entity == null) {
            return ResponseDTO.userErrorParam("需求不存在");
        }
        if (!Objects.equals(entity.getStatus(), RequirementStatusEnum.PENDING.getValue())) {
            return ResponseDTO.userErrorParam("仅" + RequirementStatusEnum.PENDING.getDesc() + "状态的需求可删除，请改为失败/作废");
        }
        // 删除关联
        repoRelDao.deleteByRequirementId(requirementId);
        statusLogDao.delete(new LambdaQueryWrapper<RequirementStatusLogEntity>()
                .eq(RequirementStatusLogEntity::getRequirementId, requirementId));
        requirementDao.deleteById(requirementId);
        return ResponseDTO.ok();
    }

    /**
     * 状态流转(页面/Service 调用通用)
     *
     * @param source 1-页面 2-MCP
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> updateStatus(RequirementStatusUpdateForm updateForm, Integer source,
                                            Long operatorId, String operatorName) {
        RequirementEntity entity = requirementDao.selectById(updateForm.getRequirementId());
        if (entity == null) {
            return ResponseDTO.userErrorParam("需求不存在");
        }
        RequirementStatusEnum targetEnum = RequirementStatusEnum.getByValue(updateForm.getStatus());
        if (targetEnum == null) {
            return ResponseDTO.userErrorParam("非法的状态值");
        }
        // 终态保护
        if (RequirementStatusEnum.isTerminal(entity.getStatus())) {
            return ResponseDTO.userErrorParam("当前需求处于终态，不允许再流转");
        }
        Integer oldStatus = entity.getStatus();
        if (Objects.equals(oldStatus, targetEnum.getValue())) {
            return ResponseDTO.userErrorParam("需求已处于该状态，无需流转");
        }
        // 更新状态
        requirementDao.update(null, new LambdaUpdateWrapper<RequirementEntity>()
                .eq(RequirementEntity::getRequirementId, entity.getRequirementId())
                .set(RequirementEntity::getStatus, targetEnum.getValue()));
        // 记录历史
        insertStatusLog(entity.getRequirementId(), oldStatus, targetEnum.getValue(), source, operatorId, operatorName, updateForm.getRemark());
        return ResponseDTO.okMsg(targetEnum.getDesc());
    }

    /**
     * 查询某需求状态流转历史
     */
    public ResponseDTO<List<RequirementStatusLogVO>> statusLogList(Long requirementId) {
        List<RequirementStatusLogVO> list = statusLogDao.queryByRequirementId(requirementId);
        list.forEach(vo -> {
            RequirementStatusEnum fromEnum = RequirementStatusEnum.getByValue(vo.getFromStatus());
            RequirementStatusEnum toEnum = RequirementStatusEnum.getByValue(vo.getToStatus());
            vo.setFromStatusDesc(fromEnum == null ? "无" : fromEnum.getDesc());
            vo.setToStatusDesc(toEnum == null ? "无" : toEnum.getDesc());
            vo.setSourceDesc(Objects.equals(vo.getSource(), RequirementStatusSourceEnum.MCP.getValue())
                    ? RequirementStatusSourceEnum.MCP.getDesc() : RequirementStatusSourceEnum.PAGE.getDesc());
        });
        return ResponseDTO.ok(list);
    }

    /**
     * MCP 列表查询(分页)
     */
    public PageResult<RequirementVO> mcpList(RequirementListQueryForm queryForm) {
        if (queryForm.getPageNum() == null || queryForm.getPageNum() < 1) {
            queryForm.setPageNum(1L);
        }
        if (queryForm.getPageSize() == null || queryForm.getPageSize() > 50) {
            queryForm.setPageSize(50L);
        }
        Page<?> page = new Page<>(queryForm.getPageNum(), queryForm.getPageSize());
        List<RequirementVO> voList = requirementDao.queryList(queryForm);
        voList.forEach(this::fillListVO);
        return SmartPageUtil.convert2PageResult(page, voList);
    }

    /**
     * 校验项目是否存在且启用(供MCP创建需求)
     */
    public RequirementProjectEntity checkProjectByCode(String projectCode) {
        if (StringUtils.isBlank(projectCode)) {
            return null;
        }
        return projectDao.selectOne(new LambdaQueryWrapper<RequirementProjectEntity>()
                .eq(RequirementProjectEntity::getProjectCode, projectCode.trim())
                .last("LIMIT 1"));
    }

    // ==================== 私有方法 ====================

    /**
     * 保存需求-工程关联(先删后插)
     */
    private void saveRepoRel(Long requirementId, Long projectId, List<Long> repoIdList) {
        repoRelDao.deleteByRequirementId(requirementId);
        if (CollectionUtils.isEmpty(repoIdList)) {
            return;
        }
        // 校验工程属于该项目且启用
        List<Long> validIds = new ArrayList<>();
        for (Long repoId : repoIdList) {
            if (repoId == null) {
                continue;
            }
            RequirementRepoEntity repo = repoDao.selectById(repoId);
            if (repo == null || !Objects.equals(repo.getProjectId(), projectId)) {
                continue; // 跨项目工程忽略
            }
            if (Objects.equals(repo.getStatus(), 1)) {
                throw new RuntimeException("存在已停用工程，请重新选择");
            }
            validIds.add(repoId);
        }
        for (Long repoId : validIds) {
            RequirementRepoRelEntity rel = new RequirementRepoRelEntity();
            rel.setRequirementId(requirementId);
            rel.setRepoId(repoId);
            repoRelDao.insert(rel);
        }
    }

    /**
     * 记录状态流转历史
     */
    private void insertStatusLog(Long requirementId, Integer fromStatus, Integer toStatus,
                                 Integer source, Long operatorId, String operatorName, String remark) {
        RequirementStatusLogEntity log = new RequirementStatusLogEntity();
        log.setRequirementId(requirementId);
        log.setFromStatus(fromStatus);
        log.setToStatus(toStatus);
        log.setOperator(operatorId);
        log.setOperatorName(operatorName);
        log.setSource(source);
        log.setRemark(remark);
        statusLogDao.insert(log);
    }

    /**
     * 验收标准列表转JSON字符串
     */
    private String toJson(List<RequirementAcceptanceForm> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        JSONArray array = new JSONArray();
        for (RequirementAcceptanceForm item : list) {
            if (item == null) {
                continue;
            }
            JSONObject obj = new JSONObject();
            obj.set("content", item.getContent());
            obj.set("given", item.getGiven());
            obj.set("when", item.getWhen());
            obj.set("then", item.getThen());
            array.add(obj);
        }
        return array.isEmpty() ? null : array.toString();
    }

    /**
     * 解析验收标准JSON
     */
    private List<RequirementAcceptanceVO> parseAcceptance(String json) {
        if (StringUtils.isBlank(json)) {
            return Collections.emptyList();
        }
        try {
            JSONArray array = JSONUtil.parseArray(json);
            List<RequirementAcceptanceVO> list = new ArrayList<>();
            for (Object item : array) {
                JSONObject obj = (JSONObject) item;
                RequirementAcceptanceVO vo = new RequirementAcceptanceVO();
                vo.setContent(obj.getStr("content"));
                vo.setGiven(obj.getStr("given"));
                vo.setWhen(obj.getStr("when"));
                vo.setThen(obj.getStr("then"));
                list.add(vo);
            }
            return list;
        } catch (Exception e) {
            log.warn("解析验收标准失败: {}", json, e);
            return Collections.emptyList();
        }
    }

    /**
     * 填充列表VO:状态描述
     */
    private void fillListVO(RequirementVO vo) {
        RequirementStatusEnum statusEnum = RequirementStatusEnum.getByValue(vo.getStatus());
        vo.setStatusDesc(statusEnum == null ? "未知" : statusEnum.getDesc());
    }

    /**
     * 填充详情VO:验收标准列表 + 关联工程
     */
    private void fillDetailVO(RequirementVO vo) {
        fillListVO(vo);
        vo.setAcceptanceCriteriaList(parseAcceptance(vo.getAcceptanceCriteria()));
        List<Long> repoIdList = repoRelDao.queryRepoIdList(vo.getRequirementId());
        vo.setRepoIdList(repoIdList);
        if (CollectionUtils.isNotEmpty(repoIdList)) {
            List<RequirementRepoEntity> repoList = repoDao.selectList(
                    new LambdaQueryWrapper<RequirementRepoEntity>().in(RequirementRepoEntity::getRepoId, repoIdList));
            List<RequirementRepoVO> repoVOList = repoList.stream().map(e -> {
                RequirementRepoVO rvo = SmartBeanUtil.copy(e, RequirementRepoVO.class);
                rvo.setStatusDesc(Objects.equals(e.getStatus(), 0) ? "启用" : "停用");
                return rvo;
            }).collect(Collectors.toList());
            vo.setRepoList(repoVOList);
        }
    }
}
