package com.gklx.mopga.admin.module.generate.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gklx.mopga.admin.module.generate.dao.MappingDataDao;
import com.gklx.mopga.admin.module.generate.domain.entity.MappingDataEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 映射数据表  Manager
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */
@Service
public class MappingDataManager extends ServiceImpl<MappingDataDao, MappingDataEntity> {


    public List<MappingDataEntity> listByMappingCode(String mappingCode) {
        LambdaQueryWrapper<MappingDataEntity> lqw = Wrappers.lambdaQuery();
        lqw.eq(MappingDataEntity::getMappingCode, mappingCode);
        lqw.orderByAsc(MappingDataEntity::getSort);
        return this.list(lqw);
    }

}
