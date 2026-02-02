package com.gklx.mopga.admin.module.generate.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.dao.TemplateCodeItemDao;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateCodeItemEntity;
import com.gklx.mopga.admin.module.generate.domain.form.LogTemplateCodeAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateCodeItemAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateCodeItemQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateCodeItemUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TemplateCodeItemVo;
import com.gklx.mopga.admin.module.generate.manager.TemplateCodeItemManager;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
import com.gklx.mopga.base.common.util.SmartPageUtil;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 代码模板项表 Service
 *
 * @Author gklx
 * @Date 2025-09-18 17:18:23
 * @Copyright gklx
 */

@Service
public class TemplateCodeItemService {

    @Resource
    private TemplateCodeItemDao templateCodeItemDao;
    @Resource
    private TemplateCodeItemManager templateCodeItemManager;
    @Resource
    private LogTemplateCodeService logTemplateCodeService;

    /**
     * 分页查询
     */
    public PageResult<TemplateCodeItemVo> queryPage(TemplateCodeItemQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<TemplateCodeItemVo> list = templateCodeItemDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }


    /**
     * 详情
     */
    public ResponseDTO<TemplateCodeItemVo> getDetail(Long id) {
        TemplateCodeItemEntity templateCodeItemEntity = templateCodeItemDao.selectById(id);
        TemplateCodeItemVo templateCodeItemVo = SmartBeanUtil.copy(templateCodeItemEntity, TemplateCodeItemVo.class);
        return ResponseDTO.ok(templateCodeItemVo);
    }

    /**
     * 添加
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> add(TemplateCodeItemAddForm addForm) {
        TemplateCodeItemEntity templateCodeItemEntity = SmartBeanUtil.copy(addForm, TemplateCodeItemEntity.class);
        templateCodeItemDao.insert(templateCodeItemEntity);
        LogTemplateCodeAddForm logTemplateCodeAddForm = new LogTemplateCodeAddForm();
        logTemplateCodeAddForm.setTemplateCodeId(templateCodeItemEntity.getId());
        logTemplateCodeAddForm.setType("1");
        logTemplateCodeAddForm.setContent(templateCodeItemEntity.getContent());
        logTemplateCodeService.add(logTemplateCodeAddForm);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(TemplateCodeItemUpdateForm updateForm) {
        TemplateCodeItemEntity templateCodeItemEntity = SmartBeanUtil.copy(updateForm, TemplateCodeItemEntity.class);
        templateCodeItemDao.updateById(templateCodeItemEntity);
        LogTemplateCodeAddForm logTemplateCodeAddForm = new LogTemplateCodeAddForm();
        logTemplateCodeAddForm.setTemplateCodeId(templateCodeItemEntity.getId());
        logTemplateCodeAddForm.setType("2");
        logTemplateCodeAddForm.setContent(templateCodeItemEntity.getContent());
        logTemplateCodeService.add(logTemplateCodeAddForm);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return ResponseDTO.ok();
        }
        for (Long id : idList) {
            LogTemplateCodeAddForm logTemplateCodeAddForm = new LogTemplateCodeAddForm();
            logTemplateCodeAddForm.setTemplateCodeId(id);
            logTemplateCodeAddForm.setType("3");
            logTemplateCodeAddForm.setContent("");
            logTemplateCodeService.add(logTemplateCodeAddForm);
        }

        templateCodeItemDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id) {
            return ResponseDTO.ok();
        }
        LogTemplateCodeAddForm logTemplateCodeAddForm = new LogTemplateCodeAddForm();
        logTemplateCodeAddForm.setTemplateCodeId(id);
        logTemplateCodeAddForm.setType("3");
        logTemplateCodeAddForm.setContent("");
        logTemplateCodeService.add(logTemplateCodeAddForm);
        templateCodeItemDao.deleteById(id);
        return ResponseDTO.ok();
    }

    public TemplateCodeItemEntity getByName(Long templateId, String fileName, String fileType) {
        LambdaQueryWrapper<TemplateCodeItemEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(TemplateCodeItemEntity::getTemplateId, templateId);
        lambdaQuery.eq(TemplateCodeItemEntity::getFileName, fileName);
        lambdaQuery.eq(TemplateCodeItemEntity::getFileType, fileType);
        return templateCodeItemManager.getOne(lambdaQuery);
    }

    public List<TemplateCodeItemEntity> listByTemplateId(Long templateId) {
        LambdaQueryWrapper<TemplateCodeItemEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(TemplateCodeItemEntity::getTemplateId, templateId);
        lambdaQuery.orderByAsc(TemplateCodeItemEntity::getSort);
        return templateCodeItemManager.list(lambdaQuery);
    }

    public ResponseDTO<String> batchSaveOrUpdate(List<TemplateCodeItemAddForm> forms) {
        if (CollectionUtils.isNotEmpty(forms)) {
            List<TemplateCodeItemEntity> templateCodeItemEntities = SmartBeanUtil.copyList(forms, TemplateCodeItemEntity.class);
            templateCodeItemManager.saveOrUpdateBatch(templateCodeItemEntities);
        }
        return ResponseDTO.ok();
    }

    public TemplateCodeItemEntity getCreateTableTemplateByTemplateId(Long templateId) {
        LambdaQueryWrapper<TemplateCodeItemEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(TemplateCodeItemEntity::getTemplateId, templateId);
        lambdaQuery.eq(TemplateCodeItemEntity::getFileName, "create.sql");
        return templateCodeItemManager.getOne(lambdaQuery);
    }
}
