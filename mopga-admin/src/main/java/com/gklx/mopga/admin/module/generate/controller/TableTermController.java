package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.form.TableTermAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TableTermQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TableTermUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TableTermVo;
import com.gklx.mopga.admin.module.generate.service.TableTermService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 表术语表 Controller
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@RestController
@Tag(name = "表术语表")
public class TableTermController {

    @Resource
    private TableTermService tableTermService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/tableTerm/queryPage")
    @SaCheckPermission("tableTerm:query")
    public ResponseDTO<PageResult<TableTermVo>> queryPage(@RequestBody @Valid TableTermQueryForm queryForm) {
        return ResponseDTO.ok(tableTermService.queryPage(queryForm));
    }

    @Operation(summary = "详情 @author gklx")
    @GetMapping("/tableTerm/getDetail/{id}")
    @SaCheckPermission("tableTerm:query")
    public ResponseDTO<TableTermVo> getDetail(@PathVariable Long id) {
        return tableTermService.getDetail(id);
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/tableTerm/add")
    @SaCheckPermission("tableTerm:add")
    public ResponseDTO<String> add(@RequestBody @Valid TableTermAddForm addForm) {
        return tableTermService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/tableTerm/update")
    @SaCheckPermission("tableTerm:update")
    public ResponseDTO<String> update(@RequestBody @Valid TableTermUpdateForm updateForm) {
        return tableTermService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/tableTerm/batchDelete")
    @SaCheckPermission("tableTerm:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return tableTermService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/tableTerm/delete/{id}")
    @SaCheckPermission("tableTerm:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return tableTermService.delete(id);
    }

}