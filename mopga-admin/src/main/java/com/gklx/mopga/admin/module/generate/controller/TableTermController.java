package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.form.TableTermAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TableTermQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TableTermUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TableTermVo;
import com.gklx.mopga.admin.module.generate.domain.entity.TableTermEntity;
import com.gklx.mopga.admin.module.generate.manager.TableTermManager;
import com.gklx.mopga.admin.module.generate.service.TableTermService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import java.util.Objects;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 表术语表 Controller
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */
@Tag(name = "表术语表")
@RestController
public class TableTermController {

    @Resource
    private TableTermService tableTermService;
    @Resource
    private TableTermManager tableTermManager;

    @Operation(summary = "分页查询")
    @PostMapping("/tableTerm/queryPage")
    @SaCheckPermission("tableTerm:query")
    public ResponseDTO<PageResult<TableTermVo>> queryPage(@RequestBody @Valid TableTermQueryForm queryForm) {
        return ResponseDTO.ok(tableTermService.queryPage(queryForm));
    }

    @Operation(summary = "详情表术语表 @author gklx")
    @GetMapping("/tableTerm/getDetail/{id}")
    @SaCheckPermission("tableTerm:query")
    public ResponseDTO<TableTermVo> getDetail(@PathVariable Long id) {
        return tableTermService.getDetail(id);
    }

    @Operation(summary = "添加表术语表 @author gklx")
    @PostMapping("/tableTerm/add")
    @SaCheckPermission("tableTerm:add")
    public ResponseDTO<String> add(@RequestBody @Valid TableTermAddForm addForm) {
        return tableTermService.add(addForm);
    }

    @Operation(summary = "更新表术语表 @author gklx")
    @PostMapping("/tableTerm/update")
    @SaCheckPermission("tableTerm:update")
    public ResponseDTO<String> update(@RequestBody @Valid TableTermUpdateForm updateForm) {
        return tableTermService.update(updateForm);
    }

    @Operation(summary = "批量删除表术语表 @author gklx")
    @PostMapping("/tableTerm/batchDelete")
    @SaCheckPermission("tableTerm:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return tableTermService.batchDelete(idList);
    }

    @Operation(summary = "单个删除表术语表 @author gklx")
    @GetMapping("/tableTerm/delete/{id}")
    @SaCheckPermission("tableTerm:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return tableTermService.delete(id);
    }

    @Operation(summary = "详情 @author gklx")
    @GetMapping("/tableTerm/getByTableId/{id}")
    public ResponseDTO<TableTermEntity> getByTableId(@PathVariable Long id) {
        return ResponseDTO.ok(tableTermManager.getByTableId(id));
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/tableTerm/addOrUpdate")
    public ResponseDTO<String> addOrUpdate(@RequestBody @Valid TableTermUpdateForm updateForm) {
        Long id = updateForm.getId();
        if (Objects.isNull(id)) {
            return tableTermService.add(SmartBeanUtil.copy(updateForm, TableTermAddForm.class));
        } else {
            return tableTermService.update(updateForm);
        }
    }

}