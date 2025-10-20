package com.gklx.mopga.admin.module.generate.service;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.dao.TemplateColumnDao;
import com.gklx.mopga.admin.module.generate.domain.entity.DatabaseEntity;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateColumnEntity;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateMappingItemEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateColumnAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateColumnQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateColumnUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TemplateColumnVo;
import com.gklx.mopga.admin.module.generate.manager.DatabaseManager;
import com.gklx.mopga.admin.module.generate.manager.TemplateColumnManager;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
import com.gklx.mopga.base.common.util.SmartPageUtil;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 模板字段 Service
 *
 * @Author gklx
 * @Date 2025-09-26 15:45:20
 * @Copyright gklx
 */

@Service
public class TemplateColumnService {

    @Resource
    private TemplateColumnDao templateColumnDao;
    @Resource
    private TemplateColumnManager templateColumnManager;
    @Resource
    private DatabaseManager databaseManager;
    @Resource
    private TemplateMappingItemService templateMappingItemService;

    /**
     * 分页查询
     */
    public PageResult<TemplateColumnVo> queryPage(TemplateColumnQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        if(ObjUtil.isNotNull(queryForm.getDatabaseId())) {
            DatabaseEntity database = databaseManager.getById(queryForm.getDatabaseId());
            queryForm.setDatabaseId(database.getId());
        }
        List<TemplateColumnVo> list = templateColumnDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(TemplateColumnAddForm addForm) {
        TemplateColumnEntity templateColumnEntity = SmartBeanUtil.copy(addForm, TemplateColumnEntity.class);
        templateColumnDao.insert(templateColumnEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     */
    public ResponseDTO<String> update(TemplateColumnUpdateForm updateForm) {
        TemplateColumnEntity templateColumnEntity = SmartBeanUtil.copy(updateForm, TemplateColumnEntity.class);
        templateColumnDao.updateById(templateColumnEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return ResponseDTO.ok();
        }

        templateColumnDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long columnId) {
        if (null == columnId) {
            return ResponseDTO.ok();
        }

        templateColumnDao.deleteById(columnId);
        return ResponseDTO.ok();
    }

    public List<TemplateColumnEntity> listBaseColumnByTemplateId(Long templateId) {
        LambdaQueryWrapper<TemplateColumnEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(TemplateColumnEntity::getTemplateId, templateId);
        lambdaQuery.eq(TemplateColumnEntity::getIsBase, true);
        lambdaQuery.orderByAsc(TemplateColumnEntity::getSort);
        return templateColumnManager.list(lambdaQuery);
    }

    public ResponseDTO<String> batchSaveOrUpdate(@Valid List<TemplateColumnAddForm> forms) {
        if (CollectionUtils.isNotEmpty(forms)) {
            List<TemplateColumnEntity> templateColumnEntities = SmartBeanUtil.copyList(forms, TemplateColumnEntity.class);
            List<TemplateMappingItemEntity> templateMappingItemEntities = templateMappingItemService.listByTemplateId(templateColumnEntities.get(0).getTemplateId());
            Map<String, TemplateMappingItemEntity> templateMappingItemEntityMap = templateMappingItemEntities.stream().collect(Collectors.toMap(e -> e.getDatabaseColumnType().toUpperCase(), e -> e));
            for (TemplateColumnEntity templateColumnEntity : templateColumnEntities) {
                if (ObjUtil.isNull(templateColumnEntity.getColumnId())) {

                    if (StrUtil.isEmpty(templateColumnEntity.getFieldName())) {
                        templateColumnEntity.setFieldName(StrUtil.lowerFirst(StrUtil.toCamelCase(templateColumnEntity.getColumnName())));
                    }
                    if (StrUtil.isEmpty(templateColumnEntity.getFieldComment())) {
                        templateColumnEntity.setFieldComment(templateColumnEntity.getColumnComment());
                    }
                    buildFileType(templateColumnEntity, templateMappingItemEntityMap);
                    if (ObjUtil.isNotEmpty(templateColumnEntity.getIsNull())) {
                        templateColumnEntity.setIsRequired(templateColumnEntity.getIsNull());
                    }
                    if (ObjUtil.isNotEmpty(templateColumnEntity.getIsInsert())) {
                        templateColumnEntity.setIsInsert(!templateColumnEntity.getIsBase());
                    }

                    if (ObjUtil.isNotEmpty(templateColumnEntity.getIsUpdate())) {
                        templateColumnEntity.setIsUpdate(templateColumnEntity.getIsPk() || !templateColumnEntity.getIsBase());
                    }
                    if (StrUtil.isNotEmpty(templateColumnEntity.getFrontComponent())) {
                        templateColumnEntity.setFrontComponent(null);
                    }

                    if (ObjUtil.isNotEmpty(templateColumnEntity.getIsWhere())) {
                        templateColumnEntity.setIsWhere(false);
                    }

                    if (StrUtil.isNotEmpty(templateColumnEntity.getWhereType())) {
                        templateColumnEntity.setWhereType(null);
                    }
                }
            }
            templateColumnManager.saveOrUpdateBatch(templateColumnEntities);
        }
        return ResponseDTO.ok();
    }

    public static void buildFileType(TemplateColumnEntity column, Map<String, TemplateMappingItemEntity> templateMappingItemEntityMap) {
        String columnType = column.getColumnType();
        TemplateMappingItemEntity templateMappingItemEntity = templateMappingItemEntityMap.get(columnType.toUpperCase());
        if (ObjUtil.isNotNull(templateMappingItemEntity)) {
            column.setFieldType(templateMappingItemEntity.getBackColumnType());
            column.setJsType(templateMappingItemEntity.getFrontColumnType());
            return;
        }
        if (columnType.contains("(") && columnType.contains(")")) {
            columnType = columnType.substring(0, columnType.indexOf("("));
            templateMappingItemEntity = templateMappingItemEntityMap.get(columnType.toUpperCase());
            if (ObjUtil.isNotNull(templateMappingItemEntity)) {
                column.setFieldType(templateMappingItemEntity.getBackColumnType());
                column.setJsType(templateMappingItemEntity.getFrontColumnType());
            }
        }
    }

}
