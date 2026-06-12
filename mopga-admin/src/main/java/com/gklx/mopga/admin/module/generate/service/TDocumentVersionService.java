package com.gklx.mopga.admin.module.generate.service;

import com.gklx.mopga.admin.module.generate.dao.TDocumentVersionDao;
import com.gklx.mopga.admin.module.generate.domain.entity.TDocumentVersionEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentVersionAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentVersionQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TDocumentVersionUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TDocumentVersionVo;
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
 * 文档版本历史表 Service
 *
 * @Author gklx
 * @Date 2026-06-12 09:55:03
 * @Copyright 1.0
 */

@Service
public class TDocumentVersionService {

    @Resource
    private TDocumentVersionDao tDocumentVersionDao;

    /**
     * 分页查询
     */
    public PageResult<TDocumentVersionVo> queryPage(TDocumentVersionQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<TDocumentVersionVo> list = tDocumentVersionDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

     /**
     * 详情
     */
    public ResponseDTO<TDocumentVersionVo> getDetail(Long id) {
        TDocumentVersionEntity tDocumentVersionEntity = tDocumentVersionDao.selectById(id);
	    TDocumentVersionVo tDocumentVersionVo = SmartBeanUtil.copy(tDocumentVersionEntity,TDocumentVersionVo.class);
	    return ResponseDTO.ok(tDocumentVersionVo);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(TDocumentVersionAddForm addForm) {
        TDocumentVersionEntity tDocumentVersionEntity = SmartBeanUtil.copy(addForm, TDocumentVersionEntity.class);
        tDocumentVersionDao.insert(tDocumentVersionEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(TDocumentVersionUpdateForm updateForm) {
        TDocumentVersionEntity tDocumentVersionEntity = SmartBeanUtil.copy(updateForm, TDocumentVersionEntity.class);
        tDocumentVersionDao.updateById(tDocumentVersionEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        tDocumentVersionDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        tDocumentVersionDao.deleteById(id);
        return ResponseDTO.ok();
    }
}