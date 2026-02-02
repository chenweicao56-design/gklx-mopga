package com.gklx.mopga.admin.module.generate.service;

import com.gklx.mopga.admin.module.generate.dao.LogTemplateCodeDao;
import com.gklx.mopga.admin.module.generate.domain.entity.LogTemplateCodeEntity;
import com.gklx.mopga.admin.module.generate.domain.form.LogTemplateCodeAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.LogTemplateCodeQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.LogTemplateCodeUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.LogTemplateCodeVo;
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
 * 模版代码日志表 Service
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Service
public class LogTemplateCodeService {

    @Resource
    private LogTemplateCodeDao logTemplateCodeDao;

    /**
     * 分页查询
     */
    public PageResult<LogTemplateCodeVo> queryPage(LogTemplateCodeQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<LogTemplateCodeVo> list = logTemplateCodeDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

     /**
     * 详情
     */
    public ResponseDTO<LogTemplateCodeVo> getDetail(Long id) {
        LogTemplateCodeEntity logTemplateCodeEntity = logTemplateCodeDao.selectById(id);
	    LogTemplateCodeVo logTemplateCodeVo = SmartBeanUtil.copy(logTemplateCodeEntity,LogTemplateCodeVo.class);
	    return ResponseDTO.ok(logTemplateCodeVo);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(LogTemplateCodeAddForm addForm) {
        LogTemplateCodeEntity logTemplateCodeEntity = SmartBeanUtil.copy(addForm, LogTemplateCodeEntity.class);
        logTemplateCodeDao.insert(logTemplateCodeEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(LogTemplateCodeUpdateForm updateForm) {
        LogTemplateCodeEntity logTemplateCodeEntity = SmartBeanUtil.copy(updateForm, LogTemplateCodeEntity.class);
        logTemplateCodeDao.updateById(logTemplateCodeEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        logTemplateCodeDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        logTemplateCodeDao.deleteById(id);
        return ResponseDTO.ok();
    }
}