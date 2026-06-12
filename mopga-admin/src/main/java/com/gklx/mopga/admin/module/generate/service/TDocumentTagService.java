package com.gklx.mopga.admin.module.generate.service;

import com.gklx.mopga.admin.module.generate.dao.TDocumentTagDao;
import com.gklx.mopga.admin.module.generate.domain.entity.TDocumentTagEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentTagAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentTagQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentTagUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TDocumentTagVo;
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
 * 标签表 Service
 *
 * @Author gklx
 * @Date 2026-06-12 09:54:50
 * @Copyright 1.0
 */

@Service
public class TDocumentTagService {

    @Resource
    private TDocumentTagDao tDocumentTagDao;

    /**
     * 分页查询
     */
    public PageResult<TDocumentTagVo> queryPage(TDocumentTagQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<TDocumentTagVo> list = tDocumentTagDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

     /**
     * 详情
     */
    public ResponseDTO<TDocumentTagVo> getDetail(Long id) {
        TDocumentTagEntity tDocumentTagEntity = tDocumentTagDao.selectById(id);
	    TDocumentTagVo tDocumentTagVo = SmartBeanUtil.copy(tDocumentTagEntity,TDocumentTagVo.class);
	    return ResponseDTO.ok(tDocumentTagVo);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(TDocumentTagAddForm addForm) {
        TDocumentTagEntity tDocumentTagEntity = SmartBeanUtil.copy(addForm, TDocumentTagEntity.class);
        tDocumentTagDao.insert(tDocumentTagEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(TDocumentTagUpdateForm updateForm) {
        TDocumentTagEntity tDocumentTagEntity = SmartBeanUtil.copy(updateForm, TDocumentTagEntity.class);
        tDocumentTagDao.updateById(tDocumentTagEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        tDocumentTagDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        tDocumentTagDao.deleteById(id);
        return ResponseDTO.ok();
    }
}