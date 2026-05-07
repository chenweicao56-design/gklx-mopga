package com.gklx.mopga.admin.module.generate.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gklx.mopga.admin.module.generate.domain.entity.TableTermEntity;
import com.gklx.mopga.admin.module.generate.dao.TableTermDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 表术语表  Manager
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */
@Service
public class TableTermManager extends ServiceImpl<TableTermDao, TableTermEntity> {

    public List<TableTermEntity> listByDatabaseId(Long databaseId, List<Long> tableIds) {
        LambdaQueryWrapper<TableTermEntity> lqw = Wrappers.lambdaQuery();
        lqw.eq(TableTermEntity::getDatabaseId, databaseId);
        if (CollectionUtils.isNotEmpty(tableIds)) {
            lqw.in(TableTermEntity::getTableId, tableIds);
        }
        return list(lqw);
    }

    public TableTermEntity getByTableId(Long tableId) {
        LambdaQueryWrapper<TableTermEntity> lqw = Wrappers.lambdaQuery();
        lqw.eq(TableTermEntity::getTableId, tableId);
        return getOne(lqw);
    }

}