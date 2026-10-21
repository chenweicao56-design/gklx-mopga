package com.gklx.mopga.admin.module.requirement.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.constant.AdminSwaggerTagConst;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementRepoAddForm;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementRepoQueryForm;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementRepoUpdateForm;
import com.gklx.mopga.admin.module.requirement.domain.vo.RequirementRepoVO;
import com.gklx.mopga.admin.module.requirement.service.RequirementRepoService;
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
 * 需求管理-工程(git仓库) Controller
 *
 * @author gklx
 */
@Slf4j
@RestController
@Tag(name = AdminSwaggerTagConst.Business.REQUIREMENT_REPO)
public class RequirementRepoController {

    @Resource
    private RequirementRepoService repoService;

    @Operation(summary = "分页查询工程")
    @PostMapping("/requirement/repo/page/query")
    @SaCheckPermission("requirement:repo:query")
    public ResponseDTO<PageResult<RequirementRepoVO>> queryPage(@RequestBody @Valid RequirementRepoQueryForm queryForm) {
        return repoService.queryPage(queryForm);
    }

    @Operation(summary = "查询工程详情")
    @GetMapping("/requirement/repo/get/{repoId}")
    @SaCheckPermission("requirement:repo:query")
    public ResponseDTO<RequirementRepoVO> getDetail(@PathVariable Long repoId) {
        return repoService.getDetail(repoId);
    }

    @Operation(summary = "查询某项目下启用工程列表(下拉)")
    @GetMapping("/requirement/repo/enabled/list/{projectId}")
    @SaCheckPermission("requirement:repo:query")
    public ResponseDTO<List<RequirementRepoVO>> enabledList(@PathVariable Long projectId) {
        return repoService.queryEnabledList(projectId);
    }

    @Operation(summary = "新建工程")
    @PostMapping("/requirement/repo/create")
    @SaCheckPermission("requirement:repo:add")
    public ResponseDTO<String> create(@RequestBody @Valid RequirementRepoAddForm addForm) {
        RequestUser requestUser = SmartRequestUtil.getRequestUser();
        addForm.setCreateUserId(requestUser.getUserId());
        addForm.setCreateUserName(requestUser.getUserName());
        return repoService.create(addForm);
    }

    @Operation(summary = "编辑工程")
    @PostMapping("/requirement/repo/update")
    @SaCheckPermission("requirement:repo:update")
    public ResponseDTO<String> update(@RequestBody @Valid RequirementRepoUpdateForm updateForm) {
        return repoService.update(updateForm);
    }

    @Operation(summary = "删除工程")
    @GetMapping("/requirement/repo/delete/{repoId}")
    @SaCheckPermission("requirement:repo:delete")
    public ResponseDTO<String> delete(@PathVariable Long repoId) {
        return repoService.delete(repoId);
    }
}
