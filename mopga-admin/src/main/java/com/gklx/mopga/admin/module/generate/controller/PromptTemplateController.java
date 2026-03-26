package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.form.PromptTemplateAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.PromptTemplateQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.PromptTemplateUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.PromptTemplateVo;
import com.gklx.mopga.admin.module.generate.service.PromptTemplateService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 提示词模版 Controller
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@RestController
@Tag(name = "提示词模版")
public class PromptTemplateController {

    @Resource
    private PromptTemplateService promptTemplateService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/promptTemplate/queryPage")
    @SaCheckPermission("promptTemplate:query")
    public ResponseDTO<PageResult<PromptTemplateVo>> queryPage(@RequestBody @Valid PromptTemplateQueryForm queryForm) {
        return ResponseDTO.ok(promptTemplateService.queryPage(queryForm));
    }

    @Operation(summary = "详情 @author gklx")
    @GetMapping("/promptTemplate/getDetail/{id}")
    @SaCheckPermission("promptTemplate:query")
    public ResponseDTO<PromptTemplateVo> getDetail(@PathVariable Long id) {
        return promptTemplateService.getDetail(id);
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/promptTemplate/add")
    @SaCheckPermission("promptTemplate:add")
    public ResponseDTO<String> add(@RequestBody @Valid PromptTemplateAddForm addForm) {
        return promptTemplateService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/promptTemplate/update")
    @SaCheckPermission("promptTemplate:update")
    public ResponseDTO<String> update(@RequestBody @Valid PromptTemplateUpdateForm updateForm) {
        return promptTemplateService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/promptTemplate/batchDelete")
    @SaCheckPermission("promptTemplate:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return promptTemplateService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/promptTemplate/delete/{id}")
    @SaCheckPermission("promptTemplate:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return promptTemplateService.delete(id);
    }

}