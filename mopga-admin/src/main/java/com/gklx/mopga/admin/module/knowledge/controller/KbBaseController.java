package com.gklx.mopga.admin.module.knowledge.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.constant.AdminSwaggerTagConst;
import com.gklx.mopga.admin.module.knowledge.domain.form.KbBaseAddForm;
import com.gklx.mopga.admin.module.knowledge.domain.form.KbBaseQueryForm;
import com.gklx.mopga.admin.module.knowledge.domain.form.KbBaseUpdateForm;
import com.gklx.mopga.admin.module.knowledge.domain.vo.KbBaseVO;
import com.gklx.mopga.admin.module.knowledge.service.KbBaseService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 知识库-知识库 Controller（F1）
 *
 * @author gklx
 */
@Slf4j
@RestController
@Tag(name = AdminSwaggerTagConst.Business.KNOWLEDGE_BASE)
public class KbBaseController {

    @Resource
    private KbBaseService baseService;

    @Operation(summary = "分页查询知识库")
    @PostMapping("/knowledge/base/page/query")
    @SaCheckPermission("kb:base:query")
    public ResponseDTO<PageResult<KbBaseVO>> queryPage(@RequestBody @Valid KbBaseQueryForm queryForm) {
        return baseService.queryPage(queryForm);
    }

    @Operation(summary = "查询全部知识库(下拉)")
    @GetMapping("/knowledge/base/all")
    @SaCheckPermission("kb:base:query")
    public ResponseDTO<List<KbBaseVO>> queryAll() {
        return baseService.queryAll();
    }

    @Operation(summary = "新建知识库")
    @PostMapping("/knowledge/base/create")
    @SaCheckPermission("kb:base:add")
    public ResponseDTO<String> create(@RequestBody @Valid KbBaseAddForm addForm) {
        return baseService.create(addForm);
    }

    @Operation(summary = "编辑知识库")
    @PostMapping("/knowledge/base/update")
    @SaCheckPermission("kb:base:update")
    public ResponseDTO<String> update(@RequestBody @Valid KbBaseUpdateForm updateForm) {
        return baseService.update(updateForm);
    }

    @Operation(summary = "删除知识库")
    @GetMapping("/knowledge/base/delete/{baseId}")
    @SaCheckPermission("kb:base:delete")
    public ResponseDTO<String> delete(@PathVariable Long baseId) {
        return baseService.delete(baseId);
    }
}
