package com.gklx.mopga.admin.module.generate.service;

import com.gklx.mopga.admin.module.generate.dao.PromptTemplateDao;
import com.gklx.mopga.admin.module.generate.domain.entity.PromptTemplateEntity;
import com.gklx.mopga.admin.module.generate.domain.form.PromptTemplateAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.PromptTemplateQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.PromptTemplateUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.PromptTemplateVo;
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
 * 提示词模版 Service
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Service
public class PromptTemplateService {

    @Resource
    private PromptTemplateDao promptTemplateDao;

    /**
     * 分页查询
     */
    public PageResult<PromptTemplateVo> queryPage(PromptTemplateQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<PromptTemplateVo> list = promptTemplateDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

     /**
     * 详情
     */
    public ResponseDTO<PromptTemplateVo> getDetail(Long id) {
        PromptTemplateEntity promptTemplateEntity = promptTemplateDao.selectById(id);
	    PromptTemplateVo promptTemplateVo = SmartBeanUtil.copy(promptTemplateEntity,PromptTemplateVo.class);
	    return ResponseDTO.ok(promptTemplateVo);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(PromptTemplateAddForm addForm) {
        PromptTemplateEntity promptTemplateEntity = SmartBeanUtil.copy(addForm, PromptTemplateEntity.class);
        promptTemplateDao.insert(promptTemplateEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(PromptTemplateUpdateForm updateForm) {
        PromptTemplateEntity promptTemplateEntity = SmartBeanUtil.copy(updateForm, PromptTemplateEntity.class);
        promptTemplateDao.updateById(promptTemplateEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        promptTemplateDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        promptTemplateDao.deleteById(id);
        return ResponseDTO.ok();
    }
}