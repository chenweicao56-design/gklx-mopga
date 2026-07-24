package com.gklx.mopga.admin.module.ai.service;

import com.gklx.mopga.admin.module.ai.dao.SkillVersionFileDao;
import com.gklx.mopga.admin.module.ai.domain.entity.SkillVersionFileEntity;
import com.gklx.mopga.admin.module.ai.domain.form.SkillVersionFileAddForm;
import com.gklx.mopga.admin.module.ai.domain.form.SkillVersionFileQueryForm;
import com.gklx.mopga.admin.module.ai.domain.form.SkillVersionFileUpdateForm;
import com.gklx.mopga.admin.module.ai.domain.vo.SkillVersionFileVo;
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
 * 技能版本文件快照表 Service
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:24
 * @Copyright 1.0
 */

@Service
public class SkillVersionFileService {

    @Resource
    private SkillVersionFileDao skillVersionFileDao;

    /**
     * 分页查询
     */
    public PageResult<SkillVersionFileVo> queryPage(SkillVersionFileQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<SkillVersionFileVo> list = skillVersionFileDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(SkillVersionFileAddForm addForm) {
        SkillVersionFileEntity skillVersionFileEntity = SmartBeanUtil.copy(addForm, SkillVersionFileEntity.class);
        skillVersionFileDao.insert(skillVersionFileEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(SkillVersionFileUpdateForm updateForm) {
        SkillVersionFileEntity skillVersionFileEntity = SmartBeanUtil.copy(updateForm, SkillVersionFileEntity.class);
        skillVersionFileDao.updateById(skillVersionFileEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        skillVersionFileDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        skillVersionFileDao.deleteById(id);
        return ResponseDTO.ok();
    }
}