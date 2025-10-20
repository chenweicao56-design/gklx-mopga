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
import com.gklx.mopga.admin.module.generate.domain.vo.TemplateVO;
import com.gklx.mopga.admin.module.generate.manager.TemplateCodeItemManager;
import com.gklx.mopga.admin.module.generate.manager.TemplateColumnManager;
import com.gklx.mopga.admin.module.generate.manager.TemplateManager;
import com.gklx.mopga.admin.module.generate.manager.TemplateMappingItemManager;
import com.gklx.mopga.admin.module.system.role.manager.RoleEmployeeManager;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
import com.gklx.mopga.base.common.util.SmartPageUtil;
import com.gklx.mopga.base.common.util.SmartRequestUtil;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 模板表 Service
 *
 * @Author gklx
 * @Date 2025-09-18 16:47:49
 * @Copyright gklx
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
    private RoleEmployeeManager roleEmployeeManager;


    /**
     * 分页查询
     */
    public PageResult<TemplateVO> queryPage(TemplateQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<TemplateVO> list = templateDao.queryPage(page, queryForm);
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
     */
    public ResponseDTO<String> update(TemplateUpdateForm updateForm) {
        TemplateEntity templateEntity = SmartBeanUtil.copy(updateForm, TemplateEntity.class);

        TemplateEntity oldTemplateEntity = templateDao.selectById(templateEntity.getId());
        String templateType = oldTemplateEntity.getTemplateType();
        if("1".equals(templateType)) {
            Long requestUserId = SmartRequestUtil.getRequestUserId();
            roleEmployeeManager.checkContainRole(requestUserId, 1L);
        }

        templateDao.updateById(templateEntity);
        return ResponseDTO.ok();
    }


    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return ResponseDTO.ok();
        }

        templateDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id) {
            return ResponseDTO.ok();
        }

        templateDao.deleteById(id);
        return ResponseDTO.ok();
    }

    public TemplateVO getById(Long templateId) {
        TemplateEntity templateEntity = templateManager.getById(templateId);
        TemplateVO templateVO = SmartBeanUtil.copy(templateEntity, TemplateVO.class);

        List<TemplateColumnEntity> templateColumnEntities = templateColumnService.listBaseColumnByTemplateId(templateId);
        templateVO.setTemplateColumns(templateColumnEntities);

        List<TemplateCodeItemEntity> templateCodeItemEntityList = templateCodeItemService.listByTemplateId(templateId);
        templateVO.setTemplateCodeItems(templateCodeItemEntityList);
        List<TemplateMappingItemEntity> templateMappingItemEntityList = templateMappingItemService.listByTemplateId(templateId);
        templateVO.setTemplateMappingItems(templateMappingItemEntityList);
        return templateVO;

    }

    @Transactional(rollbackFor = Exception.class)
    public  ResponseDTO<String> copy(TemplateUpdateForm form) {
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
}
