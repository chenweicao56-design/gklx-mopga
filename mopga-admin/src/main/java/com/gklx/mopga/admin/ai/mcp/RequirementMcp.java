package com.gklx.mopga.admin.ai.mcp;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gklx.mopga.admin.module.requirement.constant.RequirementStatusEnum;
import com.gklx.mopga.admin.module.requirement.constant.RequirementStatusSourceEnum;
import com.gklx.mopga.admin.module.requirement.domain.entity.RequirementProjectEntity;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementListQueryForm;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementStatusUpdateForm;
import com.gklx.mopga.admin.module.requirement.domain.vo.RequirementRepoVO;
import com.gklx.mopga.admin.module.requirement.domain.vo.RequirementVO;
import com.gklx.mopga.admin.module.requirement.service.RequirementService;
import com.gklx.mopga.admin.module.system.login.domain.RequestEmployee;
import com.gklx.mopga.admin.module.system.login.manager.LoginManager;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.util.SmartRequestUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MCP 工具类：需求管理
 * 供 Vibe Coding 智能体通过 spring-ai MCP 调用
 * 提供：F10 查询需求详情 / F11 查询需求列表 / F12 回写需求状态 / F13 创建需求
 *
 * @author gklx
 */
@Slf4j
@Service
public class RequirementMcp {

    @Resource
    private RequirementService requirementService;

    @Resource
    private LoginManager loginManager;

    /**
     * F10 按需求编号查询单条需求全量信息
     */
    @Tool(description = "根据需求编号查询单条需求的完整信息（含标题、描述、验收标准、归属项目、关联工程、状态），供智能体获取需求后开发。")
    public String queryRequirementDetail(
            @ToolParam(description = "需求编号，例如 PRJ20261101-01", required = true) String requirementId,
            @ToolParam(description = "标题或描述关键词（可选，编号不存在时按关键词模糊匹配返回最匹配一条）") String keyword) {
        RequirementVO vo = requirementService.getByNumber(requirementId);
        if (vo == null && StringUtils.isNotBlank(keyword)) {
            RequirementListQueryForm form = new RequirementListQueryForm();
            form.setPageNum(1L);
            form.setPageSize(1L);
            List<RequirementVO> list = requirementService.mcpList(form).getList();
            vo = list.stream().filter(e -> (e.getTitle() != null && e.getTitle().contains(keyword))
                    || (e.getContent() != null && e.getContent().contains(keyword))).findFirst().orElse(null);
        }
        if (vo == null) {
            return String.format("未找到需求：编号 %s 不存在", requirementId);
        }
        return JSONUtil.toJsonStr(vo);
    }

    /**
     * F11 按日期/项目/状态批量查询需求列表
     */
    @Tool(description = "按日期(某日)、项目编码、状态等条件批量查询需求列表，返回需求摘要信息。")
    public String queryRequirementList(
            @ToolParam(description = "日期(某日,格式yyyy-MM-dd,可选)") String date,
            @ToolParam(description = "项目编码(可选)") String projectCode,
            @ToolParam(description = "状态(可选:1-待开发 2-开发中 3-测试中 4-已完成 5-已交付 6-失败)") Integer status,
            @ToolParam(description = "页码(可选,默认1)") Long pageNum,
            @ToolParam(description = "每页数量(可选,默认50,最大50)") Long pageSize) {
        RequirementListQueryForm form = new RequirementListQueryForm();
        if (StringUtils.isNotBlank(date)) {
            try {
                form.setDate(java.time.LocalDate.parse(date.trim()));
            } catch (Exception e) {
                return "日期格式错误，请使用 yyyy-MM-dd";
            }
        }
        form.setProjectCode(projectCode);
        form.setStatus(status);
        form.setPageNum(pageNum);
        form.setPageSize(pageSize);
        PageResult<RequirementVO> result = requirementService.mcpList(form);
        JSONObject resp = new JSONObject();
        resp.set("total", result.getTotal());
        resp.set("empty", result.getEmptyFlag());
        JSONArray array = new JSONArray();
        for (RequirementVO vo : result.getList()) {
            JSONObject item = new JSONObject();
            item.set("requirementId", vo.getReqNumber());
            item.set("title", vo.getTitle());
            item.set("status", vo.getStatus());
            item.set("statusDesc", vo.getStatusDesc());
            item.set("projectName", vo.getProjectName());
            item.set("projectCode", vo.getProjectCode());
            item.set("createTime", String.valueOf(vo.getCreateTime()));
            array.add(item);
        }
        resp.set("list", array);
        return resp.toString();
    }

    /**
     * F12 回写需求状态
     */
    @Tool(description = "更新需求状态并记录流转历史。允许状态：1-待开发 2-开发中 3-测试中 4-已完成 5-已交付 6-失败。终态(已交付/失败)不可再流转。")
    public String updateRequirementStatus(
            @ToolParam(description = "需求编号,例如 PRJ20261101-01", required = true) String requirementId,
            @ToolParam(description = "目标状态(1-待开发 2-开发中 3-测试中 4-已完成 5-已交付 6-失败)", required = true) Integer status,
            @ToolParam(description = "备注(可选,如交付说明、失败原因)") String remark) {
        RequirementVO vo = requirementService.getByNumber(requirementId);
        if (vo == null) {
            return String.format("未找到需求：编号 %s 不存在", requirementId);
        }
        RequirementStatusEnum statusEnum = RequirementStatusEnum.getByValue(status);
        if (statusEnum == null) {
            return String.format("非法的状态值 %s，允许的状态：待开发/开发中/测试中/已完成/已交付/失败", status);
        }
        mcpLogin();
        RequirementStatusUpdateForm form = new RequirementStatusUpdateForm();
        form.setRequirementId(vo.getRequirementId());
        form.setStatus(status);
        form.setRemark(remark);
        com.gklx.mopga.base.common.domain.ResponseDTO<String> result =
                requirementService.updateStatus(form, RequirementStatusSourceEnum.MCP.getValue(), 1L, "MCP");
        if (result.getOk()) {
            return String.format("需求 %s 状态已更新为：%s（%s）", requirementId, statusEnum.getDesc(), result.getMsg());
        }
        return String.format("状态回写失败：%s", result.getMsg());
    }

    /**
     * F13 创建需求
     */
    @Tool(description = "创建一条新需求，自动生成需求编号并返回。需提供项目编码与标题。")
    public String createRequirement(
            @ToolParam(description = "项目编码(必填,需存在且启用,作为编号前缀)", required = true) String projectCode,
            @ToolParam(description = "需求标题(必填)", required = true) String title,
            @ToolParam(description = "需求描述/正文(可选)") String content,
            @ToolParam(description = "验收标准列表(可选,格式为 [{given,when,then,content}])") String acceptanceCriteria,
            @ToolParam(description = "关联工程ID列表(可选,需属于该项目)") List<Long> repoIdList) {
        RequirementProjectEntity project = requirementService.checkProjectByCode(projectCode);
        if (project == null) {
            return String.format("项目编码 %s 不存在", projectCode);
        }
        if (project.getStatus() != null && project.getStatus() == 1) {
            return String.format("项目 %s 已停用，无法创建需求", projectCode);
        }
        if (StringUtils.isBlank(title)) {
            return "需求标题不能为空";
        }
        mcpLogin();
        com.gklx.mopga.admin.module.requirement.domain.form.RequirementAddForm form =
                new com.gklx.mopga.admin.module.requirement.domain.form.RequirementAddForm();
        form.setProjectId(project.getProjectId());
        form.setTitle(title);
        form.setContent(content);
        form.setRepoIdList(repoIdList);
        form.setCreateBy(1L);
        form.setCreateByName("MCP");
        // 解析验收标准
        if (StringUtils.isNotBlank(acceptanceCriteria)) {
            try {
                JSONArray array = JSONUtil.parseArray(acceptanceCriteria);
                java.util.List<com.gklx.mopga.admin.module.requirement.domain.form.RequirementAcceptanceForm> list =
                        new java.util.ArrayList<>();
                for (Object o : array) {
                    JSONObject obj = (JSONObject) o;
                    com.gklx.mopga.admin.module.requirement.domain.form.RequirementAcceptanceForm item =
                            new com.gklx.mopga.admin.module.requirement.domain.form.RequirementAcceptanceForm();
                    item.setContent(obj.getStr("content"));
                    item.setGiven(obj.getStr("given"));
                    item.setWhen(obj.getStr("when"));
                    item.setThen(obj.getStr("then"));
                    list.add(item);
                }
                form.setAcceptanceCriteria(list);
            } catch (Exception e) {
                return "验收标准格式错误，应为 JSON 数组";
            }
        }
        com.gklx.mopga.base.common.domain.ResponseDTO<String> result = requirementService.create(form);
        if (result.getOk()) {
            return String.format("需求创建成功，需求编号：%s", result.getMsg());
        }
        return String.format("需求创建失败：%s", result.getMsg());
    }

    /**
     * MCP 调用时以固定系统用户身份登录(沿用现有 GenerateMcp 模式)
     */
    private void mcpLogin() {
        RequestEmployee requestEmployee = loginManager.getRequestEmployee(1L);
        if (requestEmployee != null) {
            SmartRequestUtil.setRequestUser(requestEmployee);
        }
    }
}
