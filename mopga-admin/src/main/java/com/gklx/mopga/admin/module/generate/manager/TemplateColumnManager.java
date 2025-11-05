package com.gklx.mopga.admin.module.generate.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gklx.mopga.admin.module.generate.dao.TemplateColumnDao;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateColumnEntity;
import org.springframework.stereotype.Service;

/**
 * 模板字段  Manager
 *
 * @Author gklx
 * @Date 2025-09-26 15:45:20
 * @Copyright gklx
 */
@Service
public class TemplateColumnManager extends ServiceImpl<TemplateColumnDao, TemplateColumnEntity> {


    public void deleteByTemplateId(Long id) {
        LambdaQueryWrapper<TemplateColumnEntity> lqw = Wrappers.lambdaQuery();
        lqw.eq(TemplateColumnEntity::getTemplateId, id);
        this.remove(lqw);
    }
}
