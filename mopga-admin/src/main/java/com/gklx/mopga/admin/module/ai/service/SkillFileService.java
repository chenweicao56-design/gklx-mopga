package com.gklx.mopga.admin.module.ai.service;

import com.gklx.mopga.admin.module.ai.dao.SkillFileDao;
import com.gklx.mopga.admin.module.ai.domain.entity.SkillFileEntity;
import com.gklx.mopga.admin.module.ai.domain.form.SkillFileAddForm;
import com.gklx.mopga.admin.module.ai.domain.form.SkillFileQueryForm;
import com.gklx.mopga.admin.module.ai.domain.form.SkillFileUpdateForm;
import com.gklx.mopga.admin.module.ai.domain.vo.SkillFileVo;
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
 * 技能草稿文件表 Service
 *
 * @Author gklx
 * @Date 2026-07-24 15:07:23
 * @Copyright 1.0
 */

@Service
public class SkillFileService {

    @Resource
    private SkillFileDao skillFileDao;

    /**
     * 分页查询
     */
    public PageResult<SkillFileVo> queryPage(SkillFileQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<SkillFileVo> list = skillFileDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(SkillFileAddForm addForm) {
        SkillFileEntity skillFileEntity = SmartBeanUtil.copy(addForm, SkillFileEntity.class);
        skillFileDao.insert(skillFileEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(SkillFileUpdateForm updateForm) {
        SkillFileEntity skillFileEntity = SmartBeanUtil.copy(updateForm, SkillFileEntity.class);
        skillFileDao.updateById(skillFileEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        skillFileDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        skillFileDao.deleteById(id);
        return ResponseDTO.ok();
    }
}