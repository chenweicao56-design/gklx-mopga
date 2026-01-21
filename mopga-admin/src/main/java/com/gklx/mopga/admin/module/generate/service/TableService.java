package com.gklx.mopga.admin.module.generate.service;

import com.gklx.mopga.admin.module.generate.dao.TableDao;
import com.gklx.mopga.admin.module.generate.domain.entity.TableEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TableAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TableQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TableUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TableVo;
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
 * 表 Service
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Service
public class TableService {

    @Resource
    private TableDao tableDao;

    /**
     * 分页查询
     */
    public PageResult<TableVo> queryPage(TableQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<TableVo> list = tableDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

     /**
     * 详情
     */
    public ResponseDTO<TableVo> getDetail(Long tableId) {
        TableEntity tableEntity = tableDao.selectById(tableId);
	    TableVo tableVo = SmartBeanUtil.copy(tableEntity,TableVo.class);
	    return ResponseDTO.ok(tableVo);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(TableAddForm addForm) {
        TableEntity tableEntity = SmartBeanUtil.copy(addForm, TableEntity.class);
        tableDao.insert(tableEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(TableUpdateForm updateForm) {
        TableEntity tableEntity = SmartBeanUtil.copy(updateForm, TableEntity.class);
        tableDao.updateById(tableEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        tableDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long tableId) {
        if (null == tableId){
            return ResponseDTO.ok();
        }
        tableDao.deleteById(tableId);
        return ResponseDTO.ok();
    }
}