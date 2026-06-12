package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentTagAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentTagQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentTagUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TDocumentTagVo;
import com.gklx.mopga.admin.module.generate.service.TDocumentTagService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 标签表 Controller
 *
 * @Author gklx
 * @Date 2026-06-12 09:54:50
 * @Copyright 1.0
 */

@RestController
@Tag(name = "标签表")
public class TDocumentTagController {

    @Resource
    private TDocumentTagService tDocumentTagService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/tDocumentTag/queryPage")
    @SaCheckPermission("tDocumentTag:query")
    public ResponseDTO<PageResult<TDocumentTagVo>> queryPage(@RequestBody @Valid TDocumentTagQueryForm queryForm) {
        return ResponseDTO.ok(tDocumentTagService.queryPage(queryForm));
    }

    @Operation(summary = "详情 @author gklx")
    @GetMapping("/tDocumentTag/getDetail/{id}")
    @SaCheckPermission("tDocumentTag:query")
    public ResponseDTO<TDocumentTagVo> getDetail(@PathVariable Long id) {
        return tDocumentTagService.getDetail(id);
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/tDocumentTag/add")
    @SaCheckPermission("tDocumentTag:add")
    public ResponseDTO<String> add(@RequestBody @Valid TDocumentTagAddForm addForm) {
        return tDocumentTagService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/tDocumentTag/update")
    @SaCheckPermission("tDocumentTag:update")
    public ResponseDTO<String> update(@RequestBody @Valid TDocumentTagUpdateForm updateForm) {
        return tDocumentTagService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/tDocumentTag/batchDelete")
    @SaCheckPermission("tDocumentTag:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return tDocumentTagService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/tDocumentTag/delete/{id}")
    @SaCheckPermission("tDocumentTag:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return tDocumentTagService.delete(id);
    }

}