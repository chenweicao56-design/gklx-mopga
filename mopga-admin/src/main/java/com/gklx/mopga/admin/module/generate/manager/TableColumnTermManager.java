package com.gklx.mopga.admin.module.generate.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gklx.mopga.admin.module.generate.domain.entity.TableColumnTermEntity;
import com.gklx.mopga.admin.module.generate.dao.TableColumnTermDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

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

    public List<TableColumnTermEntity> listByTableTermIds(List<Long> tableTermIds) {
        if (CollectionUtils.isEmpty(tableTermIds)) {
            return new java.util.ArrayList<>();
        }
        LambdaQueryWrapper<TableColumnTermEntity> lqw = Wrappers.lambdaQuery();
        lqw.in(TableColumnTermEntity::getTableTermId, tableTermIds);
        return list(lqw);
    }
}