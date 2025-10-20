package com.gklx.mopga.admin.module.generate.service;

import com.gklx.mopga.admin.module.generate.dao.DatabaseDao;
import com.gklx.mopga.admin.module.generate.domain.entity.DatabaseEntity;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.DatabaseUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.DatabaseVo;
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
 * 数据源表 Service
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Service
public class DatabaseService {

    @Resource
    private DatabaseDao databaseDao;

    /**
     * 分页查询
     */
    public PageResult<DatabaseVo> queryPage(DatabaseQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<DatabaseVo> list = databaseDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(DatabaseAddForm addForm) {
        DatabaseEntity databaseEntity = SmartBeanUtil.copy(addForm, DatabaseEntity.class);
        databaseDao.insert(databaseEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(DatabaseUpdateForm updateForm) {
        DatabaseEntity databaseEntity = SmartBeanUtil.copy(updateForm, DatabaseEntity.class);
        databaseDao.updateById(databaseEntity);
        return ResponseDTO.ok();
    }
    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        databaseDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        databaseDao.deleteById(id);
        return ResponseDTO.ok();
    }

    public DatabaseVo get(Long databaseId) {
        DatabaseEntity databaseEntity = databaseDao.selectById(databaseId);
        return SmartBeanUtil.copy(databaseEntity, DatabaseVo.class);
    }
}
