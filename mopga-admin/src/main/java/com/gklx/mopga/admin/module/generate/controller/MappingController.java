package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.form.MappingAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.MappingQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.MappingUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.MappingVo;
import com.gklx.mopga.admin.module.generate.service.MappingService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 映射表 Controller
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@RestController
@Tag(name = "映射表")
public class MappingController {

    @Resource
    private MappingService mappingService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/mapping/queryPage")
    @SaCheckPermission("mapping:query")
    public ResponseDTO<PageResult<MappingVo>> queryPage(@RequestBody @Valid MappingQueryForm queryForm) {
        return ResponseDTO.ok(mappingService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/mapping/add")
    @SaCheckPermission("mapping:add")
    public ResponseDTO<String> add(@RequestBody @Valid MappingAddForm addForm) {
        return mappingService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/mapping/update")
    @SaCheckPermission("mapping:update")
    public ResponseDTO<String> update(@RequestBody @Valid MappingUpdateForm updateForm) {
        return mappingService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/mapping/batchDelete")
    @SaCheckPermission("mapping:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return mappingService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/mapping/delete/{id}")
    @SaCheckPermission("mapping:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return mappingService.delete(id);
    }

}
