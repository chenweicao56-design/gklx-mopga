package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.entity.MappingDataEntity;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseTermAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.DatabaseVo;
import com.gklx.mopga.admin.module.generate.service.DatabaseService;
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
 * 数据源表 Controller
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@RestController
@Tag(name = "数据源表")
public class DatabaseController {

    @Resource
    private DatabaseService databaseService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/database/queryPage")
    @SaCheckPermission("database:query")
    public ResponseDTO<PageResult<DatabaseVo>> queryPage(@RequestBody @Valid DatabaseQueryForm queryForm) {
        return ResponseDTO.ok(databaseService.queryPage(queryForm));
    }

    @Operation(summary = "详情 @author gklx")
    @GetMapping("/database/getDetail/{id}")
    @SaCheckPermission("database:query")
    public ResponseDTO<DatabaseVo> getDetail(@PathVariable Long id) {
        return databaseService.getDetail(id);
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/database/add")
    @SaCheckPermission("database:add")
    public ResponseDTO<String> add(@RequestBody @Valid DatabaseAddForm addForm) {
        return databaseService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/database/update")
    @SaCheckPermission("database:update")
    public ResponseDTO<String> update(@RequestBody @Valid DatabaseUpdateForm updateForm) {
        return databaseService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/database/batchDelete")
    @SaCheckPermission("database:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return databaseService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/database/delete/{id}")
    @SaCheckPermission("database:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return databaseService.delete(id);
    }

    @Operation(summary = "获取相关的数据库字典类型 @author gklx")
    @GetMapping("/database/getColumnTypes/{databaseId}")
    @SaCheckPermission("database:query")
    public ResponseDTO<List<MappingDataEntity>> getColumnTypes(@PathVariable Long databaseId) {
        return ResponseDTO.ok(databaseService.getColumnTypes(databaseId));
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/databaseTerm/init")
    public ResponseDTO<String> init(@RequestBody @Valid DatabaseTermAddForm addForm) {
        return databaseService.initText2sql(addForm.getDatabaseId());
    }


}