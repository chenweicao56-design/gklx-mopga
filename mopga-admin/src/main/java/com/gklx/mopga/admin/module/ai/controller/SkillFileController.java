package com.gklx.mopga.admin.module.ai.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.ai.domain.form.SkillFileAddForm;
import com.gklx.mopga.admin.module.ai.domain.form.SkillFileQueryForm;
import com.gklx.mopga.admin.module.ai.domain.form.SkillFileUpdateForm;
import com.gklx.mopga.admin.module.ai.domain.vo.SkillFileVo;
import com.gklx.mopga.admin.module.ai.service.SkillFileService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 技能草稿文件表 Controller
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:23
 * @Copyright 1.0
 */

@RestController
@Tag(name = "技能草稿文件表")
public class SkillFileController {

    @Resource
    private SkillFileService skillFileService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/skillFile/queryPage")
    @SaCheckPermission("skillFile:query")
    public ResponseDTO<PageResult<SkillFileVo>> queryPage(@RequestBody @Valid SkillFileQueryForm queryForm) {
        return ResponseDTO.ok(skillFileService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/skillFile/add")
    @SaCheckPermission("skillFile:add")
    public ResponseDTO<String> add(@RequestBody @Valid SkillFileAddForm addForm) {
        return skillFileService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/skillFile/update")
    @SaCheckPermission("skillFile:update")
    public ResponseDTO<String> update(@RequestBody @Valid SkillFileUpdateForm updateForm) {
        return skillFileService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/skillFile/batchDelete")
    @SaCheckPermission("skillFile:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return skillFileService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/skillFile/delete/{id}")
    @SaCheckPermission("skillFile:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return skillFileService.delete(id);
    }

}