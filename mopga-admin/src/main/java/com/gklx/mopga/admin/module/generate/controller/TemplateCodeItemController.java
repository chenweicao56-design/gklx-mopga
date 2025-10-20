package com.gklx.mopga.admin.module.generate.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gklx.mopga.admin.module.generate.domain.entity.DatabaseEntity;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateCodeItemEntity;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateCodeItemAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateCodeItemQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateCodeItemUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TableVO;
import com.gklx.mopga.admin.module.generate.domain.vo.TemplateCodeItemVO;
import com.gklx.mopga.admin.module.generate.manager.DatabaseManager;
import com.gklx.mopga.admin.module.generate.manager.TemplateCodeItemManager;
import com.gklx.mopga.admin.module.generate.manager.TemplateManager;
import com.gklx.mopga.admin.module.generate.service.TableService;
import com.gklx.mopga.admin.module.generate.service.TemplateCodeItemService;
import com.gklx.mopga.admin.module.generate.service.TemplateService;
import com.gklx.mopga.admin.module.generate.util.GenUtils;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 代码模板项表 Controller
 *
 * @Author gklx
 * @Date 2025-09-18 17:05:30
 * @Copyright gklx
 */

@RestController
@Tag(name = "代码模板项表")
public class TemplateCodeItemController {

    @Resource
    private TemplateCodeItemService templateCodeItemService;
    @Resource
    private TemplateCodeItemManager templateCodeItemManager;

    @Resource
    private TemplateManager templateManager;

    @Resource
    private TemplateService templateService;

    @Autowired
    private TableService tableService;

    @Autowired
    private DatabaseManager databaseManager;

    @Operation(summary = "分页查询 @author gklx")
    @PostMapping("/templateCodeItem/queryPage")
    @SaCheckPermission("template:query")
    public ResponseDTO<PageResult<TemplateCodeItemVO>> queryPage(@RequestBody @Valid TemplateCodeItemQueryForm queryForm) {
        return ResponseDTO.ok(templateCodeItemService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author gklx")
    @PostMapping("/templateCodeItem/add")
    @SaCheckPermission("template:add")
    public ResponseDTO<String> add(@RequestBody @Valid TemplateCodeItemAddForm addForm) {
        templateService.checkEditPermission(addForm.getTemplateId());
        return templateCodeItemService.add(addForm);
    }

    @Operation(summary = "更新 @author gklx")
    @PostMapping("/templateCodeItem/update")
    @SaCheckPermission("template:update")
    public ResponseDTO<String> update(@RequestBody @Valid TemplateCodeItemUpdateForm updateForm) {
        templateService.checkEditPermission(updateForm.getTemplateId());
        return templateCodeItemService.update(updateForm);
    }

    @Operation(summary = "添加/更新 @author gklx")
    @PostMapping("/templateCodeItem/batchSaveOrUpdate")
    @SaCheckPermission("template:update")
    public ResponseDTO<String> batchSaveOrUpdate(@RequestBody @Valid List<TemplateCodeItemAddForm> forms) {
        templateService.checkEditPermission(forms.get(0).getTemplateId());
        return templateCodeItemService.batchSaveOrUpdate(forms);
    }

    @Operation(summary = "批量删除 @author gklx")
    @PostMapping("/templateCodeItem/batchDelete")
    @SaCheckPermission("template:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        TemplateCodeItemEntity templateCodeItemEntity = templateCodeItemManager.getById(idList.get(0));
        templateService.checkEditPermission(templateCodeItemEntity.getTemplateId());
        return templateCodeItemService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author gklx")
    @GetMapping("/templateCodeItem/delete/{id}")
    @SaCheckPermission("template:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        TemplateCodeItemEntity templateCodeItemEntity = templateCodeItemManager.getById(id);
        templateService.checkEditPermission(templateCodeItemEntity.getTemplateId());
        return templateCodeItemService.delete(id);
    }


    @Operation(summary = "测试模板 @author gklx")
    @PostMapping("/templateCodeItem/test")
    public ResponseDTO<String> testTemplate(@RequestBody @Valid TemplateCodeItemAddForm templateCodeItemAddForm) {
        TemplateCodeItemEntity templateCodeItemEntity = SmartBeanUtil.copy(templateCodeItemAddForm, TemplateCodeItemEntity.class);
        Long templateId = templateCodeItemEntity.getTemplateId();
        TemplateEntity template = templateManager.getById(templateId);
        List<TemplateCodeItemEntity> templateCodeItemEntities = templateCodeItemService.listByTemplateId(templateId);
        TableVO table = tableService.getById(1L);
        DatabaseEntity database = databaseManager.getById(1L);
        return ResponseDTO.ok(GenUtils.generateCode(database, table, template, templateCodeItemEntity, templateCodeItemEntities));
    }

}
