package com.gklx.mopga.admin.module.generate.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.dao.MappingDataDao;
import com.gklx.mopga.admin.module.generate.domain.entity.MappingDataEntity;
import com.gklx.mopga.admin.module.generate.domain.form.MappingDataAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.MappingDataQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.MappingDataUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.MappingDataVo;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
import com.gklx.mopga.base.common.util.SmartPageUtil;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 映射数据表 Service
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Service
public class MappingDataService {

    @Resource
    private MappingDataDao mappingDataDao;

    /**
     * 分页查询
     */
    public PageResult<MappingDataVo> queryPage(MappingDataQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<MappingDataVo> list = mappingDataDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(MappingDataAddForm addForm) {
        MappingDataEntity mappingDataEntity = SmartBeanUtil.copy(addForm, MappingDataEntity.class);
        mappingDataDao.insert(mappingDataEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(MappingDataUpdateForm updateForm) {
        MappingDataEntity mappingDataEntity = SmartBeanUtil.copy(updateForm, MappingDataEntity.class);
        mappingDataDao.updateById(mappingDataEntity);
        return ResponseDTO.ok();
    }
    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        mappingDataDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        mappingDataDao.deleteById(id);
        return ResponseDTO.ok();
    }

}
