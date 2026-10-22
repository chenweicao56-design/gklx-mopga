package com.gklx.mopga.admin.module.ai.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.ai.domain.form.ReqRepoAddForm;
import com.gklx.mopga.admin.module.ai.domain.form.ReqRepoQueryForm;
import com.gklx.mopga.admin.module.ai.domain.form.ReqRepoUpdateForm;
import com.gklx.mopga.admin.module.ai.domain.vo.ReqRepoVo;
import com.gklx.mopga.admin.module.ai.service.ReqRepoService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 工程（代码仓库）表 Controller
 *
 * @Author gklx
 * @Date 2026-10-19 14:28:17
 * @Copyright 1.0
 */

@RestController
@Tag(name = "工程（代码仓库）表")
public class ReqRepoController {

    @Resource
    private ReqRepoService reqRepoService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/reqRepo/queryPage")
    @SaCheckPermission("reqRepo:query")
    public ResponseDTO<PageResult<ReqRepoVo>> queryPage(@RequestBody @Valid ReqRepoQueryForm queryForm) {
        return ResponseDTO.ok(reqRepoService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/reqRepo/add")
    @SaCheckPermission("reqRepo:add")
    public ResponseDTO<String> add(@RequestBody @Valid ReqRepoAddForm addForm) {
        return reqRepoService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/reqRepo/update")
    @SaCheckPermission("reqRepo:update")
    public ResponseDTO<String> update(@RequestBody @Valid ReqRepoUpdateForm updateForm) {
        return reqRepoService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/reqRepo/batchDelete")
    @SaCheckPermission("reqRepo:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return reqRepoService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/reqRepo/delete/{id}")
    @SaCheckPermission("reqRepo:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return reqRepoService.delete(id);
    }

}