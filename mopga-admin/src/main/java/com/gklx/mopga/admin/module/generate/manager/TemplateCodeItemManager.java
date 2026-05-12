package com.gklx.mopga.admin.module.generate.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gklx.mopga.admin.module.generate.dao.TemplateCodeItemDao;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateCodeItemEntity;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateColumnEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateCommonUpdateForm;
import org.springframework.stereotype.Service;

/**
 * 代码模板项表  Manager
 *
 * @Author gklx
 * @Date 2025-09-18 17:05:30
 * @Copyright gklx
 */
@Service
public class TemplateCodeItemManager extends ServiceImpl<TemplateCodeItemDao, TemplateCodeItemEntity> {


    public void deleteByTemplateId(Long id) {
        LambdaQueryWrapper<TemplateCodeItemEntity> lqw = Wrappers.lambdaQuery();
        lqw.eq(TemplateCodeItemEntity::getTemplateId, id);
        this.remove(lqw);
    }

    public void sync(TemplateCommonUpdateForm updateForm) {
        LambdaUpdateWrapper<TemplateCodeItemEntity> luw = Wrappers.lambdaUpdate();
        luw.eq(TemplateCodeItemEntity::getCommonTemplateId, updateForm.getId());
        luw.eq(TemplateCodeItemEntity::getIsSync, true);
        luw.set(TemplateCodeItemEntity::getContent, updateForm.getContent());
        this.update(luw);
    }
}
