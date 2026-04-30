package com.gklx.mopga.admin.module.generate.service;

import com.gklx.mopga.admin.module.generate.dao.TableColumnTermDao;
import com.gklx.mopga.admin.module.generate.domain.entity.TableColumnTermEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TableColumnTermAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TableColumnTermQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TableColumnTermUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TableColumnTermVo;
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
 * 表字段术语 Service
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Service
public class TableColumnTermService {

    @Resource
    private TableColumnTermDao tableColumnTermDao;

    /**
     * 分页查询
     */
    public PageResult<TableColumnTermVo> queryPage(TableColumnTermQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<TableColumnTermVo> list = tableColumnTermDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

     /**
     * 详情
     */
    public ResponseDTO<TableColumnTermVo> getDetail(Long id) {
        TableColumnTermEntity tableColumnTermEntity = tableColumnTermDao.selectById(id);
	    TableColumnTermVo tableColumnTermVo = SmartBeanUtil.copy(tableColumnTermEntity,TableColumnTermVo.class);
	    return ResponseDTO.ok(tableColumnTermVo);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(TableColumnTermAddForm addForm) {
        TableColumnTermEntity tableColumnTermEntity = SmartBeanUtil.copy(addForm, TableColumnTermEntity.class);
        tableColumnTermDao.insert(tableColumnTermEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(TableColumnTermUpdateForm updateForm) {
        TableColumnTermEntity tableColumnTermEntity = SmartBeanUtil.copy(updateForm, TableColumnTermEntity.class);
        tableColumnTermDao.updateById(tableColumnTermEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        tableColumnTermDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        tableColumnTermDao.deleteById(id);
        return ResponseDTO.ok();
    }
}