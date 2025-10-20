package com.gklx.mopga.admin.module.generate.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.dao.TemplateDao;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateCodeItemEntity;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateColumnEntity;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateEntity;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateMappingItemEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TemplateVo;
import com.gklx.mopga.admin.module.generate.manager.TemplateCodeItemManager;
import com.gklx.mopga.admin.module.generate.manager.TemplateColumnManager;
import com.gklx.mopga.admin.module.generate.manager.TemplateManager;
import com.gklx.mopga.admin.module.system.role.dao.RoleEmployeeDao;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.exception.BusinessException;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
import com.gklx.mopga.base.common.util.SmartPageUtil;
import com.gklx.mopga.base.common.util.SmartRequestUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 模板表 Service
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Service
public class TemplateService {

    @Resource
    private TemplateDao templateDao;
    @Resource
    private TemplateManager templateManager;
    @Resource
    private TemplateColumnService templateColumnService;
    @Resource
    private TemplateColumnManager templateColumnManager;
    @Resource
    private TemplateCodeItemService templateCodeItemService;
    @Resource
    private TemplateCodeItemManager templateCodeItemManager;
    @Resource
    private TemplateMappingItemService templateMappingItemService;
    @Resource
    private RoleEmployeeDao roleEmployeeDao;


    /**
     * 分页查询
     */
    public PageResult<TemplateVo> queryPage(TemplateQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<TemplateVo> list = templateDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(TemplateAddForm addForm) {
        TemplateEntity templateEntity = SmartBeanUtil.copy(addForm, TemplateEntity.class);
        templateDao.insert(templateEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(TemplateUpdateForm updateForm) {
        TemplateEntity templateEntity = SmartBeanUtil.copy(updateForm, TemplateEntity.class);
        templateDao.updateById(templateEntity);
        return ResponseDTO.ok();
    }


    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        templateDao.deleteById(id);
        return ResponseDTO.ok();
    }

    public TemplateVo getById(Long templateId) {
        TemplateEntity templateEntity = templateManager.getById(templateId);
        TemplateVo templateVo = SmartBeanUtil.copy(templateEntity, TemplateVo.class);

        List<TemplateColumnEntity> templateColumnEntities = templateColumnService.listBaseColumnByTemplateId(templateId);
        templateVo.setTemplateColumns(templateColumnEntities);

        List<TemplateCodeItemEntity> templateCodeItemEntityList = templateCodeItemService.listByTemplateId(templateId);
        templateVo.setTemplateCodeItems(templateCodeItemEntityList);
        List<TemplateMappingItemEntity> templateMappingItemEntityList = templateMappingItemService.listByTemplateId(templateId);
        templateVo.setTemplateMappingItems(templateMappingItemEntityList);
        return templateVo;

    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> copy(TemplateUpdateForm form) {
        Long copyTemplateId = form.getId();
        TemplateEntity templateEntity = SmartBeanUtil.copy(form, TemplateEntity.class);
        templateEntity.setId(null);
        templateDao.insert(templateEntity);

        List<TemplateColumnEntity> templateColumnEntities = templateColumnService.listBaseColumnByTemplateId(copyTemplateId);
        templateColumnEntities.forEach(templateColumnEntity -> {
            templateColumnEntity.setTemplateId(templateEntity.getId());
        });
        List<TemplateCodeItemEntity> templateCodeItemEntityList = templateCodeItemService.listByTemplateId(copyTemplateId);
        templateCodeItemEntityList.forEach(templateCodeItemEntity -> {
            templateCodeItemEntity.setTemplateId(templateEntity.getId());
        });
        List<TemplateMappingItemEntity> templateMappingItemEntityList = templateMappingItemService.listByTemplateId(copyTemplateId);
        templateMappingItemEntityList.forEach(templateMappingItemEntity -> {
            templateMappingItemEntity.setTemplateId(templateEntity.getId());
        });
        templateColumnManager.saveBatch(templateColumnEntities);
        templateColumnManager.saveBatch(templateColumnEntities);
        templateCodeItemManager.saveBatch(templateCodeItemEntityList);
        return ResponseDTO.ok();
    }

    public void checkEditPermission(Long templateId) {
        TemplateEntity templateEntity = templateDao.selectById(templateId);
        Long createUserId = templateEntity.getCreateUserId();
        if (!createUserId.equals(SmartRequestUtil.getRequestUserId())) {
            throw new BusinessException("暂无操作权限");
        }
    }
}
