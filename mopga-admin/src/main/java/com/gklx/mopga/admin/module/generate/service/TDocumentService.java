package com.gklx.mopga.admin.module.generate.service;

import com.gklx.mopga.admin.module.generate.dao.TDocumentDao;
import com.gklx.mopga.admin.module.generate.domain.entity.TDocumentEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TDocumentVo;
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
 * 文档主表 Service
 *
 * @Author gklx
 * @Date 2026-06-12 09:54:33
 * @Copyright 1.0
 */

@Service
public class TDocumentService {

    @Resource
    private TDocumentDao tDocumentDao;

    /**
     * 分页查询
     */
    public PageResult<TDocumentVo> queryPage(TDocumentQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<TDocumentVo> list = tDocumentDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

     /**
     * 详情
     */
    public ResponseDTO<TDocumentVo> getDetail(Long id) {
        TDocumentEntity tDocumentEntity = tDocumentDao.selectById(id);
	    TDocumentVo tDocumentVo = SmartBeanUtil.copy(tDocumentEntity,TDocumentVo.class);
	    return ResponseDTO.ok(tDocumentVo);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(TDocumentAddForm addForm) {
        TDocumentEntity tDocumentEntity = SmartBeanUtil.copy(addForm, TDocumentEntity.class);
        tDocumentDao.insert(tDocumentEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(TDocumentUpdateForm updateForm) {
        TDocumentEntity tDocumentEntity = SmartBeanUtil.copy(updateForm, TDocumentEntity.class);
        tDocumentDao.updateById(tDocumentEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        tDocumentDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        tDocumentDao.deleteById(id);
        return ResponseDTO.ok();
    }
}