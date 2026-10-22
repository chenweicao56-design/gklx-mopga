package com.gklx.mopga.admin.module.knowledge.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.constant.AdminSwaggerTagConst;
import com.gklx.mopga.admin.module.knowledge.domain.form.KbSegmentQueryForm;
import com.gklx.mopga.admin.module.knowledge.domain.vo.KbSegmentVO;
import com.gklx.mopga.admin.module.knowledge.service.KbSegmentService;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 知识库-分段 Controller
 *
 * @author gklx
 */
@Slf4j
@RestController
@Tag(name = AdminSwaggerTagConst.Business.KNOWLEDGE_SEGMENT)
public class KbSegmentController {

    @Resource
    private KbSegmentService segmentService;

    @Operation(summary = "分页查询分段")
    @PostMapping("/knowledge/segment/page/query")
    @SaCheckPermission("kb:segment:query")
    public ResponseDTO<PageResult<KbSegmentVO>> queryPage(@RequestBody @Valid KbSegmentQueryForm queryForm) {
        return segmentService.queryPage(queryForm);
    }
}
