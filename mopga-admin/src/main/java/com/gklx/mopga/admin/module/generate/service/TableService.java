package com.gklx.mopga.admin.module.generate.service;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.dao.TableDao;
import com.gklx.mopga.admin.module.generate.domain.entity.TableEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TableAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TableQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TableUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.GenTableColumnVo;
import com.gklx.mopga.admin.module.generate.domain.vo.TableVo;
import com.gklx.mopga.admin.module.generate.manager.GenTableColumnManager;
import com.gklx.mopga.admin.module.generate.manager.TableManager;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;

import java.util.List;

import com.gklx.mopga.base.common.util.SmartBeanUtil;
import com.gklx.mopga.base.common.util.SmartPageUtil;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 表 Service
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Service
public class TableService {

    @Resource
    private TableDao tableDao;
    @Resource
    private TableManager tableManager;
    @Resource
    private GenTableColumnService genTableColumnService;
    @Autowired
    private TableTermService tableTermService;
    @Autowired
    private GenTableColumnManager genTableColumnManager;

    /**
     * 分页查询
     */
    public PageResult<TableVo> queryPage(TableQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<TableVo> list = tableDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 详情
     */
    public ResponseDTO<TableVo> getDetail(Long tableId) {
        TableEntity tableEntity = tableDao.selectById(tableId);
        TableVo tableVo = SmartBeanUtil.copy(tableEntity, TableVo.class);
        return ResponseDTO.ok(tableVo);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(TableAddForm addForm) {
        TableEntity tableEntity = SmartBeanUtil.copy(addForm, TableEntity.class);
        tableDao.insert(tableEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     */
    public ResponseDTO<String> update(TableUpdateForm updateForm) {
        TableEntity tableEntity = SmartBeanUtil.copy(updateForm, TableEntity.class);
        tableDao.updateById(tableEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return ResponseDTO.ok();
        }
        genTableColumnManager.batchDeleteByTableIds(idList);
        tableDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long tableId) {
        if (null == tableId) {
            return ResponseDTO.ok();
        }
        genTableColumnManager.batchDeleteByTableIds(List.of(tableId));
        tableTermService.deleteByTableIds(List.of(tableId));
        tableDao.deleteById(tableId);
        return ResponseDTO.ok();
    }

    public TableVo getById(Long tableId) {
        TableEntity tableEntity = tableManager.getById(tableId);
        TableVo TableVo = SmartBeanUtil.copy(tableEntity, TableVo.class);
        List<GenTableColumnVo> columns = genTableColumnManager.listByTableId(tableId);
        if (CollectionUtils.isNotEmpty(columns)) {
            TableVo.setColumns(columns);
        } else {
            TableVo.setColumns(new ArrayList<>());
        }
        return TableVo;
    }

    public TableVo getByName(Long databaseId, String name) {
        LambdaQueryWrapper<TableEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(TableEntity::getTableName, name);
        lambdaQuery.eq(TableEntity::getDatabaseId, databaseId);
        TableEntity tableEntity = tableManager.getOne(lambdaQuery);
        TableVo TableVo = SmartBeanUtil.copy(tableEntity, TableVo.class);
        if (ObjUtil.isNotNull(TableVo)) {
            List<GenTableColumnVo> columns = genTableColumnManager.listByTableId(tableEntity.getTableId());
            if (CollectionUtils.isEmpty(columns)) {
                TableVo.setColumns(columns);
            } else {
                TableVo.setColumns(new ArrayList<>());
            }
        }
        return TableVo;
    }

    public List<TableVo> getByNames(Long databaseId, List<String> tableNames) {
        LambdaQueryWrapper<TableEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.in(TableEntity::getTableName, tableNames);
        lambdaQuery.eq(TableEntity::getDatabaseId, databaseId);
        List<TableEntity> tables = tableManager.list(lambdaQuery);
        if (CollectionUtils.isEmpty(tables)) {
            return new ArrayList<>();
        } else {
            List<TableVo> tableVos = SmartBeanUtil.copyList(tables, TableVo.class);
            tableVos.forEach(tableVo -> {
                List<GenTableColumnVo> tableColumnVos = genTableColumnManager.listByTableId(tableVo.getTableId());
                if (CollectionUtils.isNotEmpty(tableColumnVos)) {
                    tableVo.setColumns(tableColumnVos);
                }
            });
            return tableVos;
        }

    }

    public List<TableVo> getAll(Long databaseId) {
        LambdaQueryWrapper<TableEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(TableEntity::getDatabaseId, databaseId);
        List<TableEntity> tableEntities = tableManager.list(lambdaQuery);
        List<TableVo> TableVoS = SmartBeanUtil.copyList(tableEntities, TableVo.class);
        List<GenTableColumnVo> columnVOS = genTableColumnManager.getByDatabaseId(databaseId);
        Map<Long, List<GenTableColumnVo>> collect = columnVOS.stream().collect(Collectors.groupingBy(GenTableColumnVo::getTableId));
        TableVoS.forEach(TableVo -> {
            TableVo.setColumns(collect.get(TableVo.getTableId()));
        });
        return TableVoS;
    }
}
