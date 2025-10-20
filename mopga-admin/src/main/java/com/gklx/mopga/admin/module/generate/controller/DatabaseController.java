package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseQueryForm;
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

/**
 * 数据源表 Controller
 *
 * @Author gklx
 * @Date 2025-09-19 13:15:40
 * @Copyright gklx
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

    @Operation(summary = "添加 @author gklx")
    @GetMapping("/database/getDetail/{databaseId}")
    @SaCheckPermission("database:query")
    public ResponseDTO<DatabaseVo> getDetail(@PathVariable Long databaseId) {
        return ResponseDTO.ok(databaseService.get(databaseId));
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

}
