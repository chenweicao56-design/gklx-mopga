package com.gklx.mopga.admin.module.ai.service;

import com.gklx.mopga.admin.module.ai.dao.ReqRepoDao;
import com.gklx.mopga.admin.module.ai.domain.entity.ReqRepoEntity;
import com.gklx.mopga.admin.module.ai.domain.form.ReqRepoAddForm;
import com.gklx.mopga.admin.module.ai.domain.form.ReqRepoQueryForm;
import com.gklx.mopga.admin.module.ai.domain.form.ReqRepoUpdateForm;
import com.gklx.mopga.admin.module.ai.domain.vo.ReqRepoVo;
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
 * 工程（代码仓库）表 Service
 *
 * @Author gklx
 * @Date 2026-10-19 14:28:17
 * @Copyright 1.0
 */

@Service
public class ReqRepoService {

    @Resource
    private ReqRepoDao reqRepoDao;

    /**
     * 分页查询
     */
    public PageResult<ReqRepoVo> queryPage(ReqRepoQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<ReqRepoVo> list = reqRepoDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(ReqRepoAddForm addForm) {
        ReqRepoEntity reqRepoEntity = SmartBeanUtil.copy(addForm, ReqRepoEntity.class);
        reqRepoDao.insert(reqRepoEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(ReqRepoUpdateForm updateForm) {
        ReqRepoEntity reqRepoEntity = SmartBeanUtil.copy(updateForm, ReqRepoEntity.class);
        reqRepoDao.updateById(reqRepoEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        reqRepoDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        reqRepoDao.deleteById(id);
        return ResponseDTO.ok();
    }

}