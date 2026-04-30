package com.gklx.mopga.admin.module.generate.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gklx.mopga.admin.module.generate.dao.DatabaseTermDao;
import com.gklx.mopga.admin.module.generate.domain.entity.DatabaseEntity;
import com.gklx.mopga.admin.module.generate.domain.entity.DatabaseTermEntity;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseTermAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseTermQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseTermUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.DatabaseTermVo;

import java.util.List;
import java.util.Objects;

import com.gklx.mopga.admin.module.generate.manager.DatabaseManager;
import com.gklx.mopga.admin.module.generate.manager.DatabaseTermManager;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
import com.gklx.mopga.base.common.util.SmartPageUtil;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.conf.PropertyDefinitions;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.collections4.CollectionUtils;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 数据源术语表 Service
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Service
public class DatabaseTermService {

    @Resource
    private DatabaseTermDao databaseTermDao;

    @Resource
    private DatabaseTermManager databaseTermManager;
    @Resource
    private DatabaseManager databaseManager;

    @Resource
    private TableTermService tableTermService;

    /**
     * 分页查询
     */
    public PageResult<DatabaseTermVo> queryPage(DatabaseTermQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<DatabaseTermVo> list = databaseTermDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 详情
     */
    public ResponseDTO<DatabaseTermVo> getDetail(Long id) {
        DatabaseTermEntity databaseTermEntity = databaseTermDao.selectById(id);
        DatabaseTermVo databaseTermVo = SmartBeanUtil.copy(databaseTermEntity, DatabaseTermVo.class);
        return ResponseDTO.ok(databaseTermVo);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(DatabaseTermAddForm addForm) {
        DatabaseTermEntity databaseTermEntity = SmartBeanUtil.copy(addForm, DatabaseTermEntity.class);
        databaseTermDao.insert(databaseTermEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     */
    public ResponseDTO<String> update(DatabaseTermUpdateForm updateForm) {
        DatabaseTermEntity databaseTermEntity = SmartBeanUtil.copy(updateForm, DatabaseTermEntity.class);
        databaseTermDao.updateById(databaseTermEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return ResponseDTO.ok();
        }
        databaseTermDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id) {
            return ResponseDTO.ok();
        }
        databaseTermDao.deleteById(id);
        return ResponseDTO.ok();
    }


}