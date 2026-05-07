package com.gklx.mopga.admin.module.generate.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gklx.mopga.admin.module.generate.dao.TableColumnTermDao;
import com.gklx.mopga.admin.module.generate.dao.TableTermDao;
import com.gklx.mopga.admin.module.generate.domain.entity.TableTermEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TableTermAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.TableTermQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.TableTermUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.TableTermVo;

import java.util.List;

import com.gklx.mopga.admin.module.generate.manager.TableColumnTermManager;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
import com.gklx.mopga.base.common.util.SmartPageUtil;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 表术语表 Service
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Service
public class TableTermService {

    @Resource
    private TableTermDao tableTermDao;
    @Resource
    private TableColumnTermManager tableColumnTermManager;

    /**
     * 分页查询
     */
    public PageResult<TableTermVo> queryPage(TableTermQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<TableTermVo> list = tableTermDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 详情
     */
    public ResponseDTO<TableTermVo> getDetail(Long id) {
        TableTermEntity tableTermEntity = tableTermDao.selectById(id);
        TableTermVo tableTermVo = SmartBeanUtil.copy(tableTermEntity, TableTermVo.class);
        return ResponseDTO.ok(tableTermVo);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(TableTermAddForm addForm) {
        TableTermEntity tableTermEntity = SmartBeanUtil.copy(addForm, TableTermEntity.class);
        tableTermDao.insert(tableTermEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     */
    public ResponseDTO<String> update(TableTermUpdateForm updateForm) {
        TableTermEntity tableTermEntity = SmartBeanUtil.copy(updateForm, TableTermEntity.class);
        tableTermDao.updateById(tableTermEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return ResponseDTO.ok();
        }
        tableTermDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id) {
            return ResponseDTO.ok();
        }
        tableTermDao.deleteById(id);
        return ResponseDTO.ok();
    }

    public int deleteByTableIds(List<Long> ids) {
        tableColumnTermManager.deleteByTableIds(ids);
        LambdaQueryWrapper<TableTermEntity> lqw = Wrappers.lambdaQuery();
        lqw.in(TableTermEntity::getTableId, ids);
        return tableTermDao.delete(lqw);
    }
}