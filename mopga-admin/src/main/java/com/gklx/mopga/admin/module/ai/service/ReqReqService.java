package com.gklx.mopga.admin.module.ai.service;

import com.gklx.mopga.admin.module.ai.dao.ReqReqDao;
import com.gklx.mopga.admin.module.ai.domain.entity.ReqReqEntity;
import com.gklx.mopga.admin.module.ai.domain.form.ReqReqAddForm;
import com.gklx.mopga.admin.module.ai.domain.form.ReqReqQueryForm;
import com.gklx.mopga.admin.module.ai.domain.form.ReqReqUpdateForm;
import com.gklx.mopga.admin.module.ai.domain.vo.ReqReqVo;
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
 * 需求表 Service
 *
 * @Author gklx
 * @Date 2026-10-19 14:28:17
 * @Copyright 1.0
 */

@Service
public class ReqReqService {

    @Resource
    private ReqReqDao reqReqDao;

    /**
     * 分页查询
     */
    public PageResult<ReqReqVo> queryPage(ReqReqQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<ReqReqVo> list = reqReqDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(ReqReqAddForm addForm) {
        ReqReqEntity reqReqEntity = SmartBeanUtil.copy(addForm, ReqReqEntity.class);
        reqReqDao.insert(reqReqEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(ReqReqUpdateForm updateForm) {
        ReqReqEntity reqReqEntity = SmartBeanUtil.copy(updateForm, ReqReqEntity.class);
        reqReqDao.updateById(reqReqEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        reqReqDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        reqReqDao.deleteById(id);
        return ResponseDTO.ok();
    }

}