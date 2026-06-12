package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TDocumentVo;
import com.gklx.mopga.admin.module.generate.service.TDocumentService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 文档主表 Controller
 *
 * @Author gklx
 * @Date 2026-06-12 09:54:33
 * @Copyright 1.0
 */

@RestController
@Tag(name = "文档主表")
public class TDocumentController {

    @Resource
    private TDocumentService tDocumentService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/tDocument/queryPage")
    @SaCheckPermission("tDocument:query")
    public ResponseDTO<PageResult<TDocumentVo>> queryPage(@RequestBody @Valid TDocumentQueryForm queryForm) {
        return ResponseDTO.ok(tDocumentService.queryPage(queryForm));
    }

    @Operation(summary = "详情 @author gklx")
    @GetMapping("/tDocument/getDetail/{id}")
    @SaCheckPermission("tDocument:query")
    public ResponseDTO<TDocumentVo> getDetail(@PathVariable Long id) {
        return tDocumentService.getDetail(id);
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/tDocument/add")
    @SaCheckPermission("tDocument:add")
    public ResponseDTO<String> add(@RequestBody @Valid TDocumentAddForm addForm) {
        return tDocumentService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/tDocument/update")
    @SaCheckPermission("tDocument:update")
    public ResponseDTO<String> update(@RequestBody @Valid TDocumentUpdateForm updateForm) {
        return tDocumentService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/tDocument/batchDelete")
    @SaCheckPermission("tDocument:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return tDocumentService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/tDocument/delete/{id}")
    @SaCheckPermission("tDocument:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return tDocumentService.delete(id);
    }

}