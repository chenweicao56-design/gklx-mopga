package com.gklx.mopga.admin.module.requirement.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.constant.AdminSwaggerTagConst;
import com.gklx.mopga.admin.module.requirement.constant.RequirementStatusSourceEnum;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementAddForm;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementQueryForm;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementStatusUpdateForm;
import com.gklx.mopga.admin.module.requirement.domain.form.RequirementUpdateForm;
import com.gklx.mopga.admin.module.requirement.domain.vo.RequirementStatusLogVO;
import com.gklx.mopga.admin.module.requirement.domain.vo.RequirementVO;
import com.gklx.mopga.admin.module.requirement.service.RequirementService;
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
 * 需求管理-需求 Controller
 *
 * @author gklx
 */
@Slf4j
@RestController
@Tag(name = AdminSwaggerTagConst.Business.REQUIREMENT)
public class RequirementController {

    @Resource
    private RequirementService requirementService;

    @Operation(summary = "分页查询需求")
    @PostMapping("/requirement/page/query")
    @SaCheckPermission("requirement:req:query")
    public ResponseDTO<PageResult<RequirementVO>> queryPage(@RequestBody @Valid RequirementQueryForm queryForm) {
        return requirementService.queryPage(queryForm);
    }

    @Operation(summary = "查询需求详情")
    @GetMapping("/requirement/get/{requirementId}")
    @SaCheckPermission("requirement:req:query")
    public ResponseDTO<RequirementVO> getDetail(@PathVariable Long requirementId) {
        return requirementService.getDetail(requirementId);
    }

    @Operation(summary = "新建需求")
    @PostMapping("/requirement/create")
    @SaCheckPermission("requirement:req:add")
    public ResponseDTO<String> create(@RequestBody @Valid RequirementAddForm addForm) {
        RequestUser requestUser = SmartRequestUtil.getRequestUser();
        addForm.setCreateBy(requestUser.getUserId());
        addForm.setCreateByName(requestUser.getUserName());
        return requirementService.create(addForm);
    }

    @Operation(summary = "编辑需求")
    @PostMapping("/requirement/update")
    @SaCheckPermission("requirement:req:update")
    public ResponseDTO<String> update(@RequestBody @Valid RequirementUpdateForm updateForm) {
        return requirementService.update(updateForm);
    }

    @Operation(summary = "删除需求")
    @GetMapping("/requirement/delete/{requirementId}")
    @SaCheckPermission("requirement:req:delete")
    public ResponseDTO<String> delete(@PathVariable Long requirementId) {
        return requirementService.delete(requirementId);
    }

    @Operation(summary = "需求状态流转(页面操作)")
    @PostMapping("/requirement/status/update")
    @SaCheckPermission("requirement:req:status")
    public ResponseDTO<String> updateStatus(@RequestBody @Valid RequirementStatusUpdateForm updateForm) {
        RequestUser requestUser = SmartRequestUtil.getRequestUser();
        return requirementService.updateStatus(updateForm, RequirementStatusSourceEnum.PAGE.getValue(),
                requestUser.getUserId(), requestUser.getUserName());
    }

    @Operation(summary = "查询需求状态流转历史")
    @GetMapping("/requirement/status/log/{requirementId}")
    @SaCheckPermission("requirement:req:query")
    public ResponseDTO<List<RequirementStatusLogVO>> statusLogList(@PathVariable Long requirementId) {
        return requirementService.statusLogList(requirementId);
    }
}
