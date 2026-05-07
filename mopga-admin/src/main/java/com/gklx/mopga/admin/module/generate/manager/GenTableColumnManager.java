package com.gklx.mopga.admin.module.generate.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gklx.mopga.admin.module.generate.domain.entity.GenTableColumnEntity;
import com.gklx.mopga.admin.module.generate.dao.GenTableColumnDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gklx.mopga.admin.module.generate.domain.vo.GenTableColumnVo;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
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


    public List<GenTableColumnVo> getByDatabaseId(Long databaseId) {
        LambdaQueryWrapper<GenTableColumnEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(GenTableColumnEntity::getDatabaseId, databaseId);
        return SmartBeanUtil.copyList(list(lambdaQuery), GenTableColumnVo.class);
    }

    public List<GenTableColumnVo> listByTableId(Long tableId) {
        LambdaQueryWrapper<GenTableColumnEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(GenTableColumnEntity::getTableId, tableId);
        return SmartBeanUtil.copyList(list(lambdaQuery), GenTableColumnVo.class);
    }

    public GenTableColumnEntity getByName(Long tableId, String columnName) {
        LambdaQueryWrapper<GenTableColumnEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(GenTableColumnEntity::getColumnName, columnName);
        lambdaQuery.eq(GenTableColumnEntity::getTableId, tableId);
        return getOne(lambdaQuery);
    }

    public void batchDeleteByTableIds(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        LambdaQueryWrapper<GenTableColumnEntity> lqw = Wrappers.lambdaQuery();
        lqw.in(GenTableColumnEntity::getTableId, idList);
        remove(lqw);
    }
}