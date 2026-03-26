package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateCommonAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateCommonQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateCommonUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TemplateCommonVo;
import com.gklx.mopga.admin.module.generate.service.TemplateCommonService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 公共模板 Controller
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@RestController
@Tag(name = "公共模板")
public class TemplateCommonController {

    @Resource
    private TemplateCommonService templateCommonService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/templateCommon/queryPage")
    @SaCheckPermission("templateCommon:query")
    public ResponseDTO<PageResult<TemplateCommonVo>> queryPage(@RequestBody @Valid TemplateCommonQueryForm queryForm) {
        return ResponseDTO.ok(templateCommonService.queryPage(queryForm));
    }

    @Operation(summary = "详情 @author gklx")
    @GetMapping("/templateCommon/getDetail/{id}")
    @SaCheckPermission("templateCommon:query")
    public ResponseDTO<TemplateCommonVo> getDetail(@PathVariable Long id) {
        return templateCommonService.getDetail(id);
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/templateCommon/add")
    @SaCheckPermission("templateCommon:add")
    public ResponseDTO<String> add(@RequestBody @Valid TemplateCommonAddForm addForm) {
        return templateCommonService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/templateCommon/update")
    @SaCheckPermission("templateCommon:update")
    public ResponseDTO<String> update(@RequestBody @Valid TemplateCommonUpdateForm updateForm) {
        return templateCommonService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/templateCommon/batchDelete")
    @SaCheckPermission("templateCommon:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return templateCommonService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/templateCommon/delete/{id}")
    @SaCheckPermission("templateCommon:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return templateCommonService.delete(id);
    }

}