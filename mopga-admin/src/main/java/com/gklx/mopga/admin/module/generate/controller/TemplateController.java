package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TemplateVO;
import com.gklx.mopga.admin.module.generate.service.TemplateService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 模板表 Controller
 *
 * @Author gklx
 * @Date 2025-09-18 16:47:49
 * @Copyright gklx
 */

@RestController
@Tag(name = "模板表")
public class TemplateController {

    @Resource
    private TemplateService templateService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/template/queryPage")
    @SaCheckPermission("template:query")
    public ResponseDTO<PageResult<TemplateVO>> queryPage(@RequestBody @Valid TemplateQueryForm queryForm) {
        return ResponseDTO.ok(templateService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/template/add")
    @SaCheckPermission("template:add")
    public ResponseDTO<String> add(@RequestBody @Valid TemplateAddForm addForm) {
        return templateService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/template/update")
    @SaCheckPermission("template:update")
    public ResponseDTO<String> update(@RequestBody @Valid TemplateUpdateForm updateForm) {
        return templateService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/template/batchDelete")
    @SaCheckPermission("template:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return templateService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/template/delete/{id}")
    @SaCheckPermission("template:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return templateService.delete(id);
    }


    @Operation(summary = "添加 @author gklx")
    @PostMapping("/template/copy")
    @SaCheckPermission("template:add")
    public ResponseDTO<String> copy(@RequestBody TemplateUpdateForm from) {
        return templateService.copy(from);
    }

}
