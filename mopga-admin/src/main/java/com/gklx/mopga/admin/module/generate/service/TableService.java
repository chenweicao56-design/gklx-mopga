package com.gklx.mopga.admin.module.generate.service;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.dao.TableDao;
import com.gklx.mopga.admin.module.generate.domain.entity.GenTableColumnEntity;
import com.gklx.mopga.admin.module.generate.domain.entity.TableEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TableAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TableQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TableUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.GenTableColumnVo;
import com.gklx.mopga.admin.module.generate.domain.vo.TableVo;
import com.gklx.mopga.admin.module.generate.manager.TableManager;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
import com.gklx.mopga.base.common.util.SmartPageUtil;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    /**
     * 分页查询
     */
    public PageResult<TableVo> queryPage(TableQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<TableVo> list = tableDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
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
     *
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
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        tableDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long tableId) {
        if (null == tableId){
            return ResponseDTO.ok();
        }
        tableDao.deleteById(tableId);
        return ResponseDTO.ok();
    }

    public TableVo getById(Long tableId) {
        TableEntity tableEntity = tableManager.getById(tableId);
        TableVo TableVo = SmartBeanUtil.copy(tableEntity, TableVo.class);
        List<GenTableColumnEntity> columns = genTableColumnService.listByTableId(tableId);
        if (CollectionUtils.isNotEmpty(columns)) {
            TableVo.setColumns(SmartBeanUtil.copyList(columns, GenTableColumnVo.class));
        } else {
            TableVo.setColumns(new ArrayList<>());
        }
        return TableVo;
    }

    public TableVo getByName(String name) {
        LambdaQueryWrapper<TableEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(TableEntity::getTableName, name);
        TableEntity tableEntity = tableManager.getOne(lambdaQuery);
        TableVo TableVo = SmartBeanUtil.copy(tableEntity, TableVo.class);
        if (ObjUtil.isNotNull(TableVo)) {
            List<GenTableColumnEntity> columns = genTableColumnService.listByTableId(tableEntity.getTableId());
            if (CollectionUtils.isEmpty(columns)) {
                TableVo.setColumns(SmartBeanUtil.copyList(columns, GenTableColumnVo.class));
            } else {
                TableVo.setColumns(new ArrayList<>());
            }
        }
        return TableVo;
    }

    public List<TableVo> getAll(Long databaseId) {
        LambdaQueryWrapper<TableEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(TableEntity::getDatabaseId, databaseId);
        List<TableEntity> tableEntities = tableManager.list(lambdaQuery);
        List<TableVo> TableVoS = SmartBeanUtil.copyList(tableEntities, TableVo.class);
        List<GenTableColumnVo> columnVOS = genTableColumnService.getByDatabaseId(databaseId);
        Map<Long, List<GenTableColumnVo>> collect = columnVOS.stream().collect(Collectors.groupingBy(GenTableColumnVo::getTableId));
        TableVoS.forEach(TableVo -> {
            TableVo.setColumns(collect.get(TableVo.getTableId()));
        });
        return TableVoS;
    }
}
