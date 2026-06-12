package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentTagRelationAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentTagRelationQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentTagRelationUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TDocumentTagRelationVo;
import com.gklx.mopga.admin.module.generate.service.TDocumentTagRelationService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 文档标签关联表 Controller
 *
 * @Author gklx
 * @Date 2026-06-12 09:54:56
 * @Copyright 1.0
 */

@RestController
@Tag(name = "文档标签关联表")
public class TDocumentTagRelationController {

    @Resource
    private TDocumentTagRelationService tDocumentTagRelationService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/tDocumentTagRelation/queryPage")
    @SaCheckPermission("tDocumentTagRelation:query")
    public ResponseDTO<PageResult<TDocumentTagRelationVo>> queryPage(@RequestBody @Valid TDocumentTagRelationQueryForm queryForm) {
        return ResponseDTO.ok(tDocumentTagRelationService.queryPage(queryForm));
    }

    @Operation(summary = "详情 @author gklx")
    @GetMapping("/tDocumentTagRelation/getDetail/{id}")
    @SaCheckPermission("tDocumentTagRelation:query")
    public ResponseDTO<TDocumentTagRelationVo> getDetail(@PathVariable Long id) {
        return tDocumentTagRelationService.getDetail(id);
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/tDocumentTagRelation/add")
    @SaCheckPermission("tDocumentTagRelation:add")
    public ResponseDTO<String> add(@RequestBody @Valid TDocumentTagRelationAddForm addForm) {
        return tDocumentTagRelationService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/tDocumentTagRelation/update")
    @SaCheckPermission("tDocumentTagRelation:update")
    public ResponseDTO<String> update(@RequestBody @Valid TDocumentTagRelationUpdateForm updateForm) {
        return tDocumentTagRelationService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/tDocumentTagRelation/batchDelete")
    @SaCheckPermission("tDocumentTagRelation:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return tDocumentTagRelationService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/tDocumentTagRelation/delete/{id}")
    @SaCheckPermission("tDocumentTagRelation:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return tDocumentTagRelationService.delete(id);
    }

}