package com.gklx.mopga.admin.module.generate.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.dao.GenTableColumnDao;
import com.gklx.mopga.admin.module.generate.domain.entity.GenTableColumnEntity;
import com.gklx.mopga.admin.module.generate.domain.form.GenTableColumnAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.GenTableColumnQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.GenTableColumnUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.GenTableColumnVo;
import com.gklx.mopga.admin.module.generate.manager.GenTableColumnManager;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
import com.gklx.mopga.base.common.util.SmartPageUtil;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模板 Service
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Service
public class GenTableColumnService {

    @Resource
    private GenTableColumnDao genTableColumnDao;
    @Resource
    private GenTableColumnManager genTableColumnManager;

    /**
     * 分页查询
     */
    public PageResult<GenTableColumnVo> queryPage(GenTableColumnQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<GenTableColumnVo> list = genTableColumnDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(GenTableColumnAddForm addForm) {
        GenTableColumnEntity genTableColumnEntity = SmartBeanUtil.copy(addForm, GenTableColumnEntity.class);
        genTableColumnDao.insert(genTableColumnEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(GenTableColumnUpdateForm updateForm) {
        GenTableColumnEntity genTableColumnEntity = SmartBeanUtil.copy(updateForm, GenTableColumnEntity.class);
        genTableColumnDao.updateById(genTableColumnEntity);
        return ResponseDTO.ok();
    }
    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        genTableColumnDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long columnId) {
        if (null == columnId){
            return ResponseDTO.ok();
        }
        genTableColumnDao.deleteById(columnId);
        return ResponseDTO.ok();
    }

    public ResponseDTO<String> batchDeleteByTableIds(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        LambdaQueryWrapper<GenTableColumnEntity> lqw = Wrappers.lambdaQuery();
        lqw.in(GenTableColumnEntity::getTableId, idList);
        genTableColumnDao.delete(lqw);
        return ResponseDTO.ok();
    }

    public List<GenTableColumnEntity> listByTableId(Long tableId) {
        LambdaQueryWrapper<GenTableColumnEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(GenTableColumnEntity::getTableId, tableId);
        lambdaQuery.orderByAsc(GenTableColumnEntity::getSort);
        return genTableColumnManager.list(lambdaQuery);
    }

    public GenTableColumnEntity getByName(Long tableId, String columnName) {
        LambdaQueryWrapper<GenTableColumnEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(GenTableColumnEntity::getColumnName, columnName);
        lambdaQuery.eq(GenTableColumnEntity::getTableId, tableId);
        return genTableColumnManager.getOne(lambdaQuery);
    }

    public List<GenTableColumnVo> getByDatabaseId(Long databaseId) {
        LambdaQueryWrapper<GenTableColumnEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(GenTableColumnEntity::getDatabaseId, databaseId);
       return SmartBeanUtil.copyList(genTableColumnManager.list(lambdaQuery), GenTableColumnVo.class);
    }

    public List<GenTableColumnVo> getByTableId(Long tableId) {
        LambdaQueryWrapper<GenTableColumnEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(GenTableColumnEntity::getTableId, tableId);
       return SmartBeanUtil.copyList(genTableColumnManager.list(lambdaQuery), GenTableColumnVo.class);
    }
}
