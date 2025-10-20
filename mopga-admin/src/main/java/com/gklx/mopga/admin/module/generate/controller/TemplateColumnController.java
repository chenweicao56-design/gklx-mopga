package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateColumnAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateColumnQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateColumnUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TemplateColumnVo;
import com.gklx.mopga.admin.module.generate.service.TemplateColumnService;
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
 * 模板字段 Controller
 *
 * @Author gklx
 * @Date 2025-09-26 15:45:20
 * @Copyright gklx
 */

@RestController
@Tag(name = "模板字段")
public class TemplateColumnController {

    @Resource
    private TemplateColumnService templateColumnService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/templateColumn/queryPage")
    @SaCheckPermission("template:query")
    public ResponseDTO<PageResult<TemplateColumnVo>> queryPage(@RequestBody @Valid TemplateColumnQueryForm queryForm) {
        return ResponseDTO.ok(templateColumnService.queryPage(queryForm));
    }

    @Operation(summary = "添加/更新 @author gklx")
    @PostMapping("/templateColumn/batchSaveOrUpdate")
    @SaCheckPermission("template:update")
    public ResponseDTO<String> batchSaveOrUpdate(@RequestBody @Valid List<TemplateColumnAddForm> forms) {
        return templateColumnService.batchSaveOrUpdate(forms);
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/templateColumn/add")
    @SaCheckPermission("template:add")
    public ResponseDTO<String> add(@RequestBody @Valid TemplateColumnAddForm addForm) {
        return templateColumnService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/templateColumn/update")
    @SaCheckPermission("template:update")
    public ResponseDTO<String> update(@RequestBody @Valid TemplateColumnUpdateForm updateForm) {
        return templateColumnService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/templateColumn/batchDelete")
    @SaCheckPermission("template:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return templateColumnService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/templateColumn/delete/{columnId}")
    @SaCheckPermission("template:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long columnId) {
        return templateColumnService.delete(columnId);
    }

}
