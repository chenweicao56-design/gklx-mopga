package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.form.ConversationAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.ConversationQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.ConversationUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.ConversationVo;
import com.gklx.mopga.admin.module.generate.service.ConversationService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 会话表 Controller
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@RestController
@Tag(name = "会话表")
public class ConversationController {

    @Resource
    private ConversationService conversationService;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/conversation/queryPage")
    @SaCheckPermission("conversation:query")
    public ResponseDTO<PageResult<ConversationVo>> queryPage(@RequestBody @Valid ConversationQueryForm queryForm) {
        return ResponseDTO.ok(conversationService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/conversation/add")
    @SaCheckPermission("conversation:add")
    public ResponseDTO<String> add(@RequestBody @Valid ConversationAddForm addForm) {
        return conversationService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/conversation/update")
    @SaCheckPermission("conversation:update")
    public ResponseDTO<String> update(@RequestBody @Valid ConversationUpdateForm updateForm) {
        return conversationService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/conversation/batchDelete")
    @SaCheckPermission("conversation:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return conversationService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/conversation/delete/{id}")
    @SaCheckPermission("conversation:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return conversationService.delete(id);
    }

}
