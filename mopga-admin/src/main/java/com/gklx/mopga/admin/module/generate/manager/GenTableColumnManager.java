package com.gklx.mopga.admin.module.generate.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gklx.mopga.admin.module.generate.domain.entity.GenTableColumnEntity;
import com.gklx.mopga.admin.module.generate.dao.GenTableColumnDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模板  Manager
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */
@Service
public class GenTableColumnManager extends ServiceImpl<GenTableColumnDao, GenTableColumnEntity> {

    public List<GenTableColumnEntity> listByTableIds(List<Long> tableIds) {
        if (CollectionUtils.isEmpty(tableIds)) {
            return new java.util.ArrayList<>();
        }
        LambdaQueryWrapper<GenTableColumnEntity> lqw = Wrappers.lambdaQuery();
        lqw.in(GenTableColumnEntity::getTableId, tableIds);
        return list(lqw);
    }
}