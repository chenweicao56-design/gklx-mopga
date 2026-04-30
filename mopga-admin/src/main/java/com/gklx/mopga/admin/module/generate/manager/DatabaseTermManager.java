package com.gklx.mopga.admin.module.generate.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gklx.mopga.admin.module.generate.domain.entity.DatabaseTermEntity;
import com.gklx.mopga.admin.module.generate.dao.DatabaseTermDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 数据源术语表  Manager
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */
@Service
public class DatabaseTermManager extends ServiceImpl<DatabaseTermDao, DatabaseTermEntity> {

    public DatabaseTermEntity getByDatabaseId(Long databaseId) {
        LambdaQueryWrapper<DatabaseTermEntity> lqw = Wrappers.lambdaQuery();
        return getOne(lqw);
    }
}