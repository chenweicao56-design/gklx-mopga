package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentVersionAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentVersionQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentVersionUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TDocumentVersionVo;
import com.gklx.mopga.admin.module.generate.service.TDocumentVersionService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 文档版本历史表 Controller
 *
 * @Author gklx
 * @Date 2026-06-12 09:55:03
 * @Copyright 1.0
 */

@RestController
@Tag(name = "文档版本历史表")
public class TDocumentVersionController {

    @Resource
    private TDocumentVersionService tDocumentVersionService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/tDocumentVersion/queryPage")
    @SaCheckPermission("tDocumentVersion:query")
    public ResponseDTO<PageResult<TDocumentVersionVo>> queryPage(@RequestBody @Valid TDocumentVersionQueryForm queryForm) {
        return ResponseDTO.ok(tDocumentVersionService.queryPage(queryForm));
    }

    @Operation(summary = "详情 @author gklx")
    @GetMapping("/tDocumentVersion/getDetail/{id}")
    @SaCheckPermission("tDocumentVersion:query")
    public ResponseDTO<TDocumentVersionVo> getDetail(@PathVariable Long id) {
        return tDocumentVersionService.getDetail(id);
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/tDocumentVersion/add")
    @SaCheckPermission("tDocumentVersion:add")
    public ResponseDTO<String> add(@RequestBody @Valid TDocumentVersionAddForm addForm) {
        return tDocumentVersionService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/tDocumentVersion/update")
    @SaCheckPermission("tDocumentVersion:update")
    public ResponseDTO<String> update(@RequestBody @Valid TDocumentVersionUpdateForm updateForm) {
        return tDocumentVersionService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/tDocumentVersion/batchDelete")
    @SaCheckPermission("tDocumentVersion:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return tDocumentVersionService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/tDocumentVersion/delete/{id}")
    @SaCheckPermission("tDocumentVersion:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return tDocumentVersionService.delete(id);
    }

}