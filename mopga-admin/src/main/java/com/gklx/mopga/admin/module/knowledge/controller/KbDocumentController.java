package com.gklx.mopga.admin.module.knowledge.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.constant.AdminSwaggerTagConst;
import com.gklx.mopga.admin.module.knowledge.domain.form.KbDocumentQueryForm;
import com.gklx.mopga.admin.module.knowledge.domain.form.KbDocumentRenameForm;
import com.gklx.mopga.admin.module.knowledge.domain.vo.KbDocumentVO;
import com.gklx.mopga.admin.module.knowledge.service.KbDocumentService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.RequestUser;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.util.SmartRequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 知识库-文档 Controller（F2）
 *
 * @author gklx
 */
@Slf4j
@RestController
@Tag(name = AdminSwaggerTagConst.Business.KNOWLEDGE_DOCUMENT)
public class KbDocumentController {

    @Resource
    private KbDocumentService documentService;

    @Operation(summary = "分页查询文档")
    @PostMapping("/knowledge/document/page/query")
    @SaCheckPermission("kb:doc:query")
    public ResponseDTO<PageResult<KbDocumentVO>> queryPage(@RequestBody @Valid KbDocumentQueryForm queryForm) {
        return documentService.queryPage(queryForm);
    }

    @Operation(summary = "上传文档")
    @PostMapping("/knowledge/document/upload")
    @SaCheckPermission("kb:doc:upload")
    public ResponseDTO<String> upload(@RequestParam("baseId") Long baseId,
                                      @RequestParam("file") MultipartFile file) {
        RequestUser requestUser = SmartRequestUtil.getRequestUser();
        return documentService.upload(baseId, file, requestUser);
    }

    @Operation(summary = "重命名文档")
    @PostMapping("/knowledge/document/rename")
    @SaCheckPermission("kb:doc:rename")
    public ResponseDTO<String> rename(@RequestBody @Valid KbDocumentRenameForm renameForm) {
        return documentService.rename(renameForm);
    }

    @Operation(summary = "删除文档")
    @GetMapping("/knowledge/document/delete/{documentId}")
    @SaCheckPermission("kb:doc:delete")
    public ResponseDTO<String> delete(@PathVariable Long documentId) {
        return documentService.delete(documentId);
    }

    @Operation(summary = "手动触发解析")
    @GetMapping("/knowledge/document/parse/{documentId}")
    @SaCheckPermission("kb:doc:parse")
    public ResponseDTO<String> triggerParse(@PathVariable Long documentId) {
        return documentService.triggerParse(documentId);
    }
}
