package com.gklx.mopga.admin.module.generate.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gklx.mopga.admin.module.generate.controller.MappingDataController;
import com.gklx.mopga.admin.module.generate.dao.DatabaseDao;
import com.gklx.mopga.admin.module.generate.domain.entity.DatabaseEntity;
import com.gklx.mopga.admin.module.generate.domain.entity.DatabaseTermEntity;
import com.gklx.mopga.admin.module.generate.domain.entity.MappingDataEntity;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateMappingItemEntity;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseTermAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.DatabaseVo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.gklx.mopga.admin.module.generate.manager.DatabaseManager;
import com.gklx.mopga.admin.module.generate.manager.DatabaseTermManager;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
import com.gklx.mopga.base.common.util.SmartPageUtil;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.collections4.CollectionUtils;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 数据源表 Service
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Service
public class DatabaseService {

    @Resource
    private DatabaseDao databaseDao;
    @Resource
    private TemplateMappingItemService templateMappingItemService;
    @Resource
    private MappingDataService mappingDataService;
    @Resource
    private DatabaseTermService databaseTermService;


    /**
     * 分页查询
     */
    public PageResult<DatabaseVo> queryPage(DatabaseQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<DatabaseVo> list = databaseDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

     /**
     * 详情
     */
    public ResponseDTO<DatabaseVo> getDetail(Long id) {
        DatabaseEntity databaseEntity = databaseDao.selectById(id);
	    DatabaseVo databaseVo = SmartBeanUtil.copy(databaseEntity,DatabaseVo.class);
	    return ResponseDTO.ok(databaseVo);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(DatabaseAddForm addForm) {
        DatabaseEntity databaseEntity = SmartBeanUtil.copy(addForm, DatabaseEntity.class);
        databaseDao.insert(databaseEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(DatabaseUpdateForm updateForm) {
        DatabaseEntity databaseEntity = SmartBeanUtil.copy(updateForm, DatabaseEntity.class);
        databaseDao.updateById(databaseEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        databaseDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        databaseDao.deleteById(id);
        return ResponseDTO.ok();
    }

    public DatabaseVo get(Long databaseId) {
        DatabaseEntity databaseEntity = databaseDao.selectById(databaseId);
        return SmartBeanUtil.copy(databaseEntity, DatabaseVo.class);
    }

    public List<MappingDataEntity> getColumnTypes(Long databaseId) {
        DatabaseEntity database = databaseDao.selectById(databaseId);
        String databaseType = database.getDatabaseType();
        Long templateId = database.getTemplateId();
        List<TemplateMappingItemEntity> templateMappingItemEntities = templateMappingItemService.listByTemplateId(templateId);
        List<MappingDataEntity> mappingDataEntities = mappingDataService.listByMappingCode(databaseType);
        if (CollectionUtils.isNotEmpty(templateMappingItemEntities)) {
            List<MappingDataEntity> collect = templateMappingItemEntities.stream().map(templateMappingItemEntity -> {
                MappingDataEntity mappingDataEntity = new MappingDataEntity();
                mappingDataEntity.setDatabaseFieldType(templateMappingItemEntity.getDatabaseColumnType());
                mappingDataEntity.setJavaFieldType(templateMappingItemEntity.getBackColumnType());
                mappingDataEntity.setFrontFieldType(templateMappingItemEntity.getFrontColumnType());
                mappingDataEntity.setFrontComponent(templateMappingItemEntity.getFrontComponent());
                return mappingDataEntity;
            }).toList();
            mappingDataEntities.addAll(0, collect);
        }
        return mappingDataEntities;

    }


    public ResponseDTO<String> initText2sql(Long databaseId) {
        DatabaseEntity database = databaseDao.selectById(databaseId);

        return ResponseDTO.ok();
    }
}