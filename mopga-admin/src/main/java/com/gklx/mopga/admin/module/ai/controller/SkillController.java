package com.gklx.mopga.admin.module.ai.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.ai.domain.form.SkillAddForm;
import com.gklx.mopga.admin.module.ai.domain.form.SkillQueryForm;
import com.gklx.mopga.admin.module.ai.domain.form.SkillUpdateForm;
import com.gklx.mopga.admin.module.ai.domain.vo.SkillVo;
import com.gklx.mopga.admin.module.ai.service.SkillService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 技能主表 Controller
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:22
 * @Copyright 1.0
 */

@RestController
@Tag(name = "技能主表")
public class SkillController {

    @Resource
    private SkillService skillService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/skill/queryPage")
    @SaCheckPermission("skill:query")
    public ResponseDTO<PageResult<SkillVo>> queryPage(@RequestBody @Valid SkillQueryForm queryForm) {
        return ResponseDTO.ok(skillService.queryPage(queryForm));
    }

    @Operation(summary = "详情 @author gklx")
    @GetMapping("/skill/getDetail/{id}")
    @SaCheckPermission("skill:query")
    public ResponseDTO<SkillVo> getDetail(@PathVariable Long id) {
        return skillService.getDetail(id);
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/skill/add")
    @SaCheckPermission("skill:add")
    public ResponseDTO<String> add(@RequestBody @Valid SkillAddForm addForm) {
        return skillService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/skill/update")
    @SaCheckPermission("skill:update")
    public ResponseDTO<String> update(@RequestBody @Valid SkillUpdateForm updateForm) {
        return skillService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/skill/batchDelete")
    @SaCheckPermission("skill:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return skillService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/skill/delete/{id}")
    @SaCheckPermission("skill:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return skillService.delete(id);
    }

}