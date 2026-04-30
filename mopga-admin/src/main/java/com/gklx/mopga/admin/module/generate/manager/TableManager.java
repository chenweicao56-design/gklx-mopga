package com.gklx.mopga.admin.module.generate.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gklx.mopga.admin.module.generate.domain.entity.TableEntity;
import com.gklx.mopga.admin.module.generate.dao.TableDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 表  Manager
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */
@Service
public class TableManager extends ServiceImpl<TableDao, TableEntity> {

    public List<TableEntity> listByDatabaseId(Long databaseId, List<Long> tableIds) {
        LambdaQueryWrapper<TableEntity> lqw = Wrappers.lambdaQuery();
        lqw.eq(TableEntity::getDatabaseId, databaseId);
        if (CollectionUtils.isNotEmpty(tableIds)) {
            lqw.in(TableEntity::getTableId, tableIds);
        }
        return list(lqw);
    }
}