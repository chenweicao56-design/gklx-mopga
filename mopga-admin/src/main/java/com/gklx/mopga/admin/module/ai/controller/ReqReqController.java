package com.gklx.mopga.admin.module.ai.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.ai.domain.form.ReqReqAddForm;
import com.gklx.mopga.admin.module.ai.domain.form.ReqReqQueryForm;
import com.gklx.mopga.admin.module.ai.domain.form.ReqReqUpdateForm;
import com.gklx.mopga.admin.module.ai.domain.vo.ReqReqVo;
import com.gklx.mopga.admin.module.ai.service.ReqReqService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 需求表 Controller
 *
 * @Author gklx
 * @Date 2026-10-19 14:28:17
 * @Copyright 1.0
 */

@RestController
@Tag(name = "需求表")
public class ReqReqController {

    @Resource
    private ReqReqService reqReqService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/reqReq/queryPage")
    @SaCheckPermission("reqReq:query")
    public ResponseDTO<PageResult<ReqReqVo>> queryPage(@RequestBody @Valid ReqReqQueryForm queryForm) {
        return ResponseDTO.ok(reqReqService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/reqReq/add")
    @SaCheckPermission("reqReq:add")
    public ResponseDTO<String> add(@RequestBody @Valid ReqReqAddForm addForm) {
        return reqReqService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/reqReq/update")
    @SaCheckPermission("reqReq:update")
    public ResponseDTO<String> update(@RequestBody @Valid ReqReqUpdateForm updateForm) {
        return reqReqService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/reqReq/batchDelete")
    @SaCheckPermission("reqReq:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return reqReqService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/reqReq/delete/{id}")
    @SaCheckPermission("reqReq:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return reqReqService.delete(id);
    }

}