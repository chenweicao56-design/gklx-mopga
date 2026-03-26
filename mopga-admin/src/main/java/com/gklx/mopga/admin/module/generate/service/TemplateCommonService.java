package com.gklx.mopga.admin.module.generate.service;

import com.gklx.mopga.admin.module.generate.dao.TemplateCommonDao;
import com.gklx.mopga.admin.module.generate.domain.entity.TemplateCommonEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateCommonAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateCommonQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TemplateCommonUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TemplateCommonVo;
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
 * 公共模板 Service
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Service
public class TemplateCommonService {

    @Resource
    private TemplateCommonDao templateCommonDao;

    /**
     * 分页查询
     */
    public PageResult<TemplateCommonVo> queryPage(TemplateCommonQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<TemplateCommonVo> list = templateCommonDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

     /**
     * 详情
     */
    public ResponseDTO<TemplateCommonVo> getDetail(Long id) {
        TemplateCommonEntity templateCommonEntity = templateCommonDao.selectById(id);
	    TemplateCommonVo templateCommonVo = SmartBeanUtil.copy(templateCommonEntity,TemplateCommonVo.class);
	    return ResponseDTO.ok(templateCommonVo);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(TemplateCommonAddForm addForm) {
        TemplateCommonEntity templateCommonEntity = SmartBeanUtil.copy(addForm, TemplateCommonEntity.class);
        templateCommonDao.insert(templateCommonEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(TemplateCommonUpdateForm updateForm) {
        TemplateCommonEntity templateCommonEntity = SmartBeanUtil.copy(updateForm, TemplateCommonEntity.class);
        templateCommonDao.updateById(templateCommonEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        templateCommonDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        templateCommonDao.deleteById(id);
        return ResponseDTO.ok();
    }
}