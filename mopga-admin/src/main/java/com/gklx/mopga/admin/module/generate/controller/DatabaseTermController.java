package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseTermAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseTermQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseTermUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.DatabaseTermVo;
import com.gklx.mopga.admin.module.generate.service.DatabaseTermService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 数据源术语表 Controller
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@RestController
@Tag(name = "数据源术语表")
public class DatabaseTermController {

    @Resource
    private DatabaseTermService databaseTermService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/databaseTerm/queryPage")
    @SaCheckPermission("databaseTerm:query")
    public ResponseDTO<PageResult<DatabaseTermVo>> queryPage(@RequestBody @Valid DatabaseTermQueryForm queryForm) {
        return ResponseDTO.ok(databaseTermService.queryPage(queryForm));
    }

    @Operation(summary = "详情 @author gklx")
    @GetMapping("/databaseTerm/getDetail/{id}")
    @SaCheckPermission("databaseTerm:query")
    public ResponseDTO<DatabaseTermVo> getDetail(@PathVariable Long id) {
        return databaseTermService.getDetail(id);
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/databaseTerm/add")
    @SaCheckPermission("databaseTerm:add")
    public ResponseDTO<String> add(@RequestBody @Valid DatabaseTermAddForm addForm) {
        return databaseTermService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/databaseTerm/update")
    @SaCheckPermission("databaseTerm:update")
    public ResponseDTO<String> update(@RequestBody @Valid DatabaseTermUpdateForm updateForm) {
        return databaseTermService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/databaseTerm/batchDelete")
    @SaCheckPermission("databaseTerm:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return databaseTermService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/databaseTerm/delete/{id}")
    @SaCheckPermission("databaseTerm:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return databaseTermService.delete(id);
    }




}