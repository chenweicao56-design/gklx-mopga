package com.gklx.mopga.admin.module.generate.service;

import com.gklx.mopga.admin.module.generate.dao.TDocumentTagRelationDao;
import com.gklx.mopga.admin.module.generate.domain.entity.TDocumentTagRelationEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentTagRelationAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentTagRelationQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentTagRelationUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TDocumentTagRelationVo;
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
 * 文档标签关联表 Service
 *
 * @Author gklx
 * @Date 2026-06-12 09:54:56
 * @Copyright 1.0
 */

@Service
public class TDocumentTagRelationService {

    @Resource
    private TDocumentTagRelationDao tDocumentTagRelationDao;

    /**
     * 分页查询
     */
    public PageResult<TDocumentTagRelationVo> queryPage(TDocumentTagRelationQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<TDocumentTagRelationVo> list = tDocumentTagRelationDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

     /**
     * 详情
     */
    public ResponseDTO<TDocumentTagRelationVo> getDetail(Long id) {
        TDocumentTagRelationEntity tDocumentTagRelationEntity = tDocumentTagRelationDao.selectById(id);
	    TDocumentTagRelationVo tDocumentTagRelationVo = SmartBeanUtil.copy(tDocumentTagRelationEntity,TDocumentTagRelationVo.class);
	    return ResponseDTO.ok(tDocumentTagRelationVo);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(TDocumentTagRelationAddForm addForm) {
        TDocumentTagRelationEntity tDocumentTagRelationEntity = SmartBeanUtil.copy(addForm, TDocumentTagRelationEntity.class);
        tDocumentTagRelationDao.insert(tDocumentTagRelationEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(TDocumentTagRelationUpdateForm updateForm) {
        TDocumentTagRelationEntity tDocumentTagRelationEntity = SmartBeanUtil.copy(updateForm, TDocumentTagRelationEntity.class);
        tDocumentTagRelationDao.updateById(tDocumentTagRelationEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        tDocumentTagRelationDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        tDocumentTagRelationDao.deleteById(id);
        return ResponseDTO.ok();
    }
}