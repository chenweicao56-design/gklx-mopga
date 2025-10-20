package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.form.GenTableColumnAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.GenTableColumnQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.GenTableColumnUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.GenTableColumnVo;
import com.gklx.mopga.admin.module.generate.service.GenTableColumnService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 模板 Controller
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@RestController
@Tag(name = "模板")
public class GenTableColumnController {

    @Resource
    private GenTableColumnService genTableColumnService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/genTableColumn/queryPage")
    @SaCheckPermission("database:query")
    public ResponseDTO<PageResult<GenTableColumnVo>> queryPage(@RequestBody @Valid GenTableColumnQueryForm queryForm) {
        return ResponseDTO.ok(genTableColumnService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/genTableColumn/add")
    @SaCheckPermission("database:add")
    public ResponseDTO<String> add(@RequestBody @Valid GenTableColumnAddForm addForm) {
        return genTableColumnService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/genTableColumn/update")
    @SaCheckPermission("database:update")
    public ResponseDTO<String> update(@RequestBody @Valid GenTableColumnUpdateForm updateForm) {
        return genTableColumnService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/genTableColumn/batchDelete")
    @SaCheckPermission("database:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return genTableColumnService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/genTableColumn/delete/{columnId}")
    @SaCheckPermission("database:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long columnId) {
        return genTableColumnService.delete(columnId);
    }

}
