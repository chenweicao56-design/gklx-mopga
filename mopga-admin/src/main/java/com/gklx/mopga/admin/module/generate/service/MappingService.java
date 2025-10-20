package com.gklx.mopga.admin.module.generate.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.dao.MappingDao;
import com.gklx.mopga.admin.module.generate.domain.entity.MappingEntity;
import com.gklx.mopga.admin.module.generate.domain.form.MappingAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.MappingQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.MappingUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.MappingVo;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
import com.gklx.mopga.base.common.util.SmartPageUtil;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 映射表 Service
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Service
public class MappingService {

    @Resource
    private MappingDao mappingDao;

    /**
     * 分页查询
     */
    public PageResult<MappingVo> queryPage(MappingQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<MappingVo> list = mappingDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(MappingAddForm addForm) {
        MappingEntity mappingEntity = SmartBeanUtil.copy(addForm, MappingEntity.class);
        mappingDao.insert(mappingEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(MappingUpdateForm updateForm) {
        MappingEntity mappingEntity = SmartBeanUtil.copy(updateForm, MappingEntity.class);
        mappingDao.updateById(mappingEntity);
        return ResponseDTO.ok();
    }
    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        mappingDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        mappingDao.deleteById(id);
        return ResponseDTO.ok();
    }

}
