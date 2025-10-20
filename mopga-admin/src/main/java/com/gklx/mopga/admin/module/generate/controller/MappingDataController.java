package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.form.MappingDataAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.MappingDataQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.MappingDataUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.MappingDataVo;
import com.gklx.mopga.admin.module.generate.service.MappingDataService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 映射数据表 Controller
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@RestController
@Tag(name = "映射数据表")
public class MappingDataController {

    @Resource
    private MappingDataService mappingDataService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/mappingData/queryPage")
    @SaCheckPermission("mapping:query")
    public ResponseDTO<PageResult<MappingDataVo>> queryPage(@RequestBody @Valid MappingDataQueryForm queryForm) {
        return ResponseDTO.ok(mappingDataService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/mappingData/add")
    @SaCheckPermission("mapping:add")
    public ResponseDTO<String> add(@RequestBody @Valid MappingDataAddForm addForm) {
        return mappingDataService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/mappingData/update")
    @SaCheckPermission("mapping:update")
    public ResponseDTO<String> update(@RequestBody @Valid MappingDataUpdateForm updateForm) {
        return mappingDataService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/mappingData/batchDelete")
    @SaCheckPermission("mapping:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return mappingDataService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/mappingData/delete/{id}")
    @SaCheckPermission("mapping:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return mappingDataService.delete(id);
    }

}
