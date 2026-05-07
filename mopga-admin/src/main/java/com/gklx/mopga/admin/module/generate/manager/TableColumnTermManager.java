package com.gklx.mopga.admin.module.generate.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gklx.mopga.admin.module.generate.dao.TableColumnTermDao;
import com.gklx.mopga.admin.module.generate.domain.entity.TableColumnTermEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 表字段术语  Manager
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */
@Service
public class TableColumnTermManager extends ServiceImpl<TableColumnTermDao, TableColumnTermEntity> {

    public List<TableColumnTermEntity> listByTableIds(List<Long> tableIds) {
        if (CollectionUtils.isEmpty(tableIds)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<TableColumnTermEntity> lqw = Wrappers.lambdaQuery();
        lqw.in(TableColumnTermEntity::getTableId, tableIds);
        return list(lqw);
    }

    public TableColumnTermEntity getByColumnId(Long columnId) {
        LambdaQueryWrapper<TableColumnTermEntity> lqw = Wrappers.lambdaQuery();
        lqw.eq(TableColumnTermEntity::getColumnId, columnId);
        return getOne(lqw);
    }

    public void deleteByTableIds(List<Long> ids) {
        LambdaQueryWrapper<TableColumnTermEntity> lqw = Wrappers.lambdaQuery();
        lqw.in(TableColumnTermEntity::getTableId, ids);
        remove(lqw);
    }

    public void deleteByColumnIds(List<Long> ids) {
        LambdaQueryWrapper<TableColumnTermEntity> lqw = Wrappers.lambdaQuery();
        lqw.in(TableColumnTermEntity::getColumnId, ids);
        remove(lqw);
    }
}