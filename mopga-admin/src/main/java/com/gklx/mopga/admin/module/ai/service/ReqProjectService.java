package com.gklx.mopga.admin.module.ai.service;

import com.gklx.mopga.admin.module.ai.dao.ReqProjectDao;
import com.gklx.mopga.admin.module.ai.domain.entity.ReqProjectEntity;
import com.gklx.mopga.admin.module.ai.domain.form.ReqProjectAddForm;
import com.gklx.mopga.admin.module.ai.domain.form.ReqProjectQueryForm;
import com.gklx.mopga.admin.module.ai.domain.form.ReqProjectUpdateForm;
import com.gklx.mopga.admin.module.ai.domain.vo.ReqProjectVo;
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
 * 项目表 Service
 *
 * @Author gklx
 * @Date 2026-10-19 14:28:17
 * @Copyright 1.0
 */

@Service
public class ReqProjectService {

    @Resource
    private ReqProjectDao reqProjectDao;

    /**
     * 分页查询
     */
    public PageResult<ReqProjectVo> queryPage(ReqProjectQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<ReqProjectVo> list = reqProjectDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(ReqProjectAddForm addForm) {
        ReqProjectEntity reqProjectEntity = SmartBeanUtil.copy(addForm, ReqProjectEntity.class);
        reqProjectDao.insert(reqProjectEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(ReqProjectUpdateForm updateForm) {
        ReqProjectEntity reqProjectEntity = SmartBeanUtil.copy(updateForm, ReqProjectEntity.class);
        reqProjectDao.updateById(reqProjectEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        reqProjectDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        reqProjectDao.deleteById(id);
        return ResponseDTO.ok();
    }

}