package com.gklx.mopga.admin.module.generate.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gklx.mopga.admin.module.generate.dao.TemplateMappingItemDao;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateCodeItemEntity;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateMappingItemEntity;
import org.springframework.stereotype.Service;

/**
 * 字段类型映射表  Manager
 *
 * @Author gklx
 * @Date 2025-09-18 17:18:43
 * @Copyright gklx
 */
@Service
public class TemplateMappingItemManager extends ServiceImpl<TemplateMappingItemDao, TemplateMappingItemEntity> {


    public void deleteByTemplateId(Long id) {
        LambdaQueryWrapper<TemplateMappingItemEntity> lqw = Wrappers.lambdaQuery();
        lqw.eq(TemplateMappingItemEntity::getTemplateId, id);
        this.remove(lqw);
    }
}
