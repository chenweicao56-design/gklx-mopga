package com.gklx.mopga.admin.module.generate.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.dao.TemplateMappingItemDao;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateMappingItemEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateMappingItemAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateMappingItemQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateMappingItemUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TemplateMappingItemVo;
import com.gklx.mopga.admin.module.generate.manager.TemplateMappingItemManager;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
import com.gklx.mopga.base.common.util.SmartPageUtil;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字段类型映射表 Service
 *
 * @Author gklx
 * @Date 2025-09-18 17:18:43
 * @Copyright gklx
 */

@Service
public class TemplateMappingItemService {

    @Resource
    private TemplateMappingItemDao templateMappingItemDao;
    @Resource
    private TemplateMappingItemManager templateMappingItemManager;

    /**
     * 分页查询
     */
    public PageResult<TemplateMappingItemVo> queryPage(TemplateMappingItemQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<TemplateMappingItemVo> list = templateMappingItemDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(TemplateMappingItemAddForm addForm) {
        TemplateMappingItemEntity templateMappingItemEntity = SmartBeanUtil.copy(addForm, TemplateMappingItemEntity.class);
        templateMappingItemDao.insert(templateMappingItemEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(TemplateMappingItemUpdateForm updateForm) {
        TemplateMappingItemEntity templateMappingItemEntity = SmartBeanUtil.copy(updateForm, TemplateMappingItemEntity.class);
        templateMappingItemDao.updateById(templateMappingItemEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }

        templateMappingItemDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }

        templateMappingItemDao.deleteById(id);
        return ResponseDTO.ok();
    }

    public List<TemplateMappingItemEntity> listByTemplateId(Long templateId) {
        LambdaQueryWrapper<TemplateMappingItemEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(TemplateMappingItemEntity::getTemplateId, templateId);
        lambdaQuery.orderByAsc(TemplateMappingItemEntity::getSort);
        return templateMappingItemManager.list(lambdaQuery);
    }

    public ResponseDTO<String> batchSaveOrUpdate(List<TemplateMappingItemAddForm> forms) {
        if (CollectionUtils.isNotEmpty(forms)) {
            List<TemplateMappingItemEntity> templateMappingItemEntities = SmartBeanUtil.copyList(forms, TemplateMappingItemEntity.class);
            templateMappingItemManager.saveOrUpdateBatch(templateMappingItemEntities);
        }
        return ResponseDTO.ok();
    }
}
