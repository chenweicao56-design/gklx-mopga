package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.form.TableColumnTermAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TableColumnTermQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TableColumnTermUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TableColumnTermVo;
import com.gklx.mopga.admin.module.generate.service.TableColumnTermService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 表字段术语 Controller
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@RestController
@Tag(name = "表字段术语")
public class TableColumnTermController {

    @Resource
    private TableColumnTermService tableColumnTermService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/tableColumnTerm/queryPage")
    @SaCheckPermission("tableColumnTerm:query")
    public ResponseDTO<PageResult<TableColumnTermVo>> queryPage(@RequestBody @Valid TableColumnTermQueryForm queryForm) {
        return ResponseDTO.ok(tableColumnTermService.queryPage(queryForm));
    }

    @Operation(summary = "详情 @author gklx")
    @GetMapping("/tableColumnTerm/getDetail/{id}")
    @SaCheckPermission("tableColumnTerm:query")
    public ResponseDTO<TableColumnTermVo> getDetail(@PathVariable Long id) {
        return tableColumnTermService.getDetail(id);
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/tableColumnTerm/add")
    @SaCheckPermission("tableColumnTerm:add")
    public ResponseDTO<String> add(@RequestBody @Valid TableColumnTermAddForm addForm) {
        return tableColumnTermService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/tableColumnTerm/update")
    @SaCheckPermission("tableColumnTerm:update")
    public ResponseDTO<String> update(@RequestBody @Valid TableColumnTermUpdateForm updateForm) {
        return tableColumnTermService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/tableColumnTerm/batchDelete")
    @SaCheckPermission("tableColumnTerm:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return tableColumnTermService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/tableColumnTerm/delete/{id}")
    @SaCheckPermission("tableColumnTerm:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return tableColumnTermService.delete(id);
    }

}