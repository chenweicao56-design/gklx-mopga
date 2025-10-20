package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.form.TableAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TableQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TableUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TableVO;
import com.gklx.mopga.admin.module.generate.service.TableService;
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
 * 表 Controller
 *
 * @Author gklx
 * @Date 2025-09-19 13:31:14
 * @Copyright gklx
 */

@RestController
@Tag(name = "表")
public class TableController {

    @Resource
    private TableService tableService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/table/queryPage")
    @SaCheckPermission("database:query")
    public ResponseDTO<PageResult<TableVO>> queryPage(@RequestBody @Valid TableQueryForm queryForm) {
        return ResponseDTO.ok(tableService.queryPage(queryForm));
    }
    @Operation(summary = "根据ID查询 @author gklx")
    @GetMapping("/table/{tableId}")
    @SaCheckPermission("database:query")
    public ResponseDTO<TableVO> get(@PathVariable Long tableId) {
        return ResponseDTO.ok(tableService.getById(tableId));
    }
    @Operation(summary = "all @author gklx")
    @GetMapping("/table/all/{databaseId}")
    @SaCheckPermission("database:query")
    public ResponseDTO<List<TableVO>> getAll(@PathVariable Long databaseId) {
        return ResponseDTO.ok(tableService.getAll(databaseId));
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/table/add")
    @SaCheckPermission("database:add")
    public ResponseDTO<String> add(@RequestBody @Valid TableAddForm addForm) {
        return tableService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/table/update")
    @SaCheckPermission("database:update")
    public ResponseDTO<String> update(@RequestBody @Valid TableUpdateForm updateForm) {
        return tableService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/table/batchDelete")
    @SaCheckPermission("database:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return tableService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/table/delete/{tableId}")
    @SaCheckPermission("database:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long tableId) {
        return tableService.delete(tableId);
    }

}
