package com.gklx.mopga.admin.module.ai.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.ai.domain.form.ReqProjectAddForm;
import com.gklx.mopga.admin.module.ai.domain.form.ReqProjectQueryForm;
import com.gklx.mopga.admin.module.ai.domain.form.ReqProjectUpdateForm;
import com.gklx.mopga.admin.module.ai.domain.vo.ReqProjectVo;
import com.gklx.mopga.admin.module.ai.service.ReqProjectService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 项目表 Controller
 *
 * @Author gklx
 * @Date 2026-10-19 14:28:17
 * @Copyright 1.0
 */

@RestController
@Tag(name = "项目表")
public class ReqProjectController {

    @Resource
    private ReqProjectService reqProjectService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/reqProject/queryPage")
    @SaCheckPermission("reqProject:query")
    public ResponseDTO<PageResult<ReqProjectVo>> queryPage(@RequestBody @Valid ReqProjectQueryForm queryForm) {
        return ResponseDTO.ok(reqProjectService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/reqProject/add")
    @SaCheckPermission("reqProject:add")
    public ResponseDTO<String> add(@RequestBody @Valid ReqProjectAddForm addForm) {
        return reqProjectService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/reqProject/update")
    @SaCheckPermission("reqProject:update")
    public ResponseDTO<String> update(@RequestBody @Valid ReqProjectUpdateForm updateForm) {
        return reqProjectService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/reqProject/batchDelete")
    @SaCheckPermission("reqProject:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return reqProjectService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/reqProject/delete/{id}")
    @SaCheckPermission("reqProject:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return reqProjectService.delete(id);
    }

}