package com.gklx.mopga.admin.module.ai.service;

import com.gklx.mopga.admin.module.ai.dao.SkillDao;
import com.gklx.mopga.admin.module.ai.domain.entity.SkillEntity;
import com.gklx.mopga.admin.module.ai.domain.form.SkillAddForm;
import com.gklx.mopga.admin.module.ai.domain.form.SkillQueryForm;
import com.gklx.mopga.admin.module.ai.domain.form.SkillUpdateForm;
import com.gklx.mopga.admin.module.ai.domain.vo.SkillVo;
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
 * 技能主表 Service
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:22
 * @Copyright 1.0
 */

@Service
public class SkillService {

    @Resource
    private SkillDao skillDao;

    /**
     * 分页查询
     */
    public PageResult<SkillVo> queryPage(SkillQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<SkillVo> list = skillDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

     /**
     * 详情
     */
    public ResponseDTO<SkillVo> getDetail(Long id) {
        SkillEntity skillEntity = skillDao.selectById(id);
	    SkillVo skillVo = SmartBeanUtil.copy(skillEntity,SkillVo.class);
	    return ResponseDTO.ok(skillVo);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(SkillAddForm addForm) {
        SkillEntity skillEntity = SmartBeanUtil.copy(addForm, SkillEntity.class);
        skillDao.insert(skillEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(SkillUpdateForm updateForm) {
        SkillEntity skillEntity = SmartBeanUtil.copy(updateForm, SkillEntity.class);
        skillDao.updateById(skillEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        skillDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        skillDao.deleteById(id);
        return ResponseDTO.ok();
    }
}