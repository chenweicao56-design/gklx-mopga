package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateMappingItemAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateMappingItemQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateMappingItemUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TemplateMappingItemVo;
import com.gklx.mopga.admin.module.generate.service.TemplateMappingItemService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字段类型映射表 Controller
 *
 * @Author gklx
 * @Date 2025-09-18 17:18:43
 * @Copyright gklx
 */

@RestController
@Tag(name = "字段类型映射表")
public class TemplateMappingItemController {

    @Resource
    private TemplateMappingItemService templateMappingItemService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/templateMappingItem/queryPage")
    @SaCheckPermission("template:query")
    public ResponseDTO<PageResult<TemplateMappingItemVo>> queryPage(@RequestBody @Valid TemplateMappingItemQueryForm queryForm) {
        return ResponseDTO.ok(templateMappingItemService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/templateMappingItem/add")
    @SaCheckPermission("template:add")
    public ResponseDTO<String> add(@RequestBody @Valid TemplateMappingItemAddForm addForm) {
        return templateMappingItemService.add(addForm);
    }

    @Operation(summary = "添加/更新 @author gklx")
    @PostMapping("/templateMappingItem/batchSaveOrUpdate")
    @SaCheckPermission("template:update")
    public ResponseDTO<String> batchSaveOrUpdate(@RequestBody @Valid List<TemplateMappingItemAddForm> forms) {
        return templateMappingItemService.batchSaveOrUpdate(forms);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/templateMappingItem/update")
    @SaCheckPermission("template:update")
    public ResponseDTO<String> update(@RequestBody @Valid TemplateMappingItemUpdateForm updateForm) {
        return templateMappingItemService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/templateMappingItem/batchDelete")
    @SaCheckPermission("template:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return templateMappingItemService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/templateMappingItem/delete/{id}")
    @SaCheckPermission("template:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return templateMappingItemService.delete(id);
    }

}
