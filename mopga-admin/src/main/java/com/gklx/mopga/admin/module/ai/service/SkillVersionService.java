package com.gklx.mopga.admin.module.ai.service;

import com.gklx.mopga.admin.module.ai.dao.SkillVersionDao;
import com.gklx.mopga.admin.module.ai.domain.entity.SkillVersionEntity;
import com.gklx.mopga.admin.module.ai.domain.form.SkillVersionAddForm;
import com.gklx.mopga.admin.module.ai.domain.form.SkillVersionQueryForm;
import com.gklx.mopga.admin.module.ai.domain.form.SkillVersionUpdateForm;
import com.gklx.mopga.admin.module.ai.domain.vo.SkillVersionVo;
import java.util.List;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
import com.gklx.mopga.base.common.util.SmartPageUtil;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 技能版本表 Service
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:24
 * @Copyright 1.0
 */

@Service
public class SkillVersionService {

    @Resource
    private SkillVersionDao skillVersionDao;

    /**
     * 分页查询
     */
    public PageResult<SkillVersionVo> queryPage(SkillVersionQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<SkillVersionVo> list = skillVersionDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(SkillVersionAddForm addForm) {
        SkillVersionEntity skillVersionEntity = SmartBeanUtil.copy(addForm, SkillVersionEntity.class);
        skillVersionDao.insert(skillVersionEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(SkillVersionUpdateForm updateForm) {
        SkillVersionEntity skillVersionEntity = SmartBeanUtil.copy(updateForm, SkillVersionEntity.class);
        skillVersionDao.updateById(skillVersionEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        skillVersionDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        skillVersionDao.deleteById(id);
        return ResponseDTO.ok();
    }
}