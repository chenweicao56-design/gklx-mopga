package com.gklx.mopga.admin.module.requirement.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.constant.AdminSwaggerTagConst;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementProjectAddForm;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementProjectQueryForm;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementProjectUpdateForm;
import com.gklx.mopga.admin.module.requirement.domain.vo.RequirementProjectVO;
import com.gklx.mopga.admin.module.requirement.service.RequirementProjectService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.RequestUser;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.util.SmartRequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 需求管理-项目 Controller
 *
 * @author gklx
 */
@Slf4j
@RestController
@Tag(name = AdminSwaggerTagConst.Business.REQUIREMENT_PROJECT)
public class RequirementProjectController {

    @Resource
    private RequirementProjectService projectService;

    @Operation(summary = "分页查询项目")
    @PostMapping("/requirement/project/page/query")
    @SaCheckPermission("requirement:project:query")
    public ResponseDTO<PageResult<RequirementProjectVO>> queryPage(@RequestBody @Valid RequirementProjectQueryForm queryForm) {
        return projectService.queryPage(queryForm);
    }

    @Operation(summary = "查询项目详情")
    @GetMapping("/requirement/project/get/{projectId}")
    @SaCheckPermission("requirement:project:query")
    public ResponseDTO<RequirementProjectVO> getDetail(@PathVariable Long projectId) {
        return projectService.getDetail(projectId);
    }

    @Operation(summary = "查询启用项目列表(下拉)")
    @GetMapping("/requirement/project/enabled/list")
    @SaCheckPermission("requirement:project:query")
    public ResponseDTO<List<RequirementProjectVO>> enabledList() {
        return projectService.queryEnabledList();
    }

    @Operation(summary = "新建项目")
    @PostMapping("/requirement/project/create")
    @SaCheckPermission("requirement:project:add")
    public ResponseDTO<String> create(@RequestBody @Valid RequirementProjectAddForm addForm) {
        RequestUser requestUser = SmartRequestUtil.getRequestUser();
        addForm.setCreateUserId(requestUser.getUserId());
        addForm.setCreateUserName(requestUser.getUserName());
        return projectService.create(addForm);
    }

    @Operation(summary = "编辑项目")
    @PostMapping("/requirement/project/update")
    @SaCheckPermission("requirement:project:update")
    public ResponseDTO<String> update(@RequestBody @Valid RequirementProjectUpdateForm updateForm) {
        return projectService.update(updateForm);
    }

    @Operation(summary = "删除项目")
    @GetMapping("/requirement/project/delete/{projectId}")
    @SaCheckPermission("requirement:project:delete")
    public ResponseDTO<String> delete(@PathVariable Long projectId) {
        return projectService.delete(projectId);
    }
}
