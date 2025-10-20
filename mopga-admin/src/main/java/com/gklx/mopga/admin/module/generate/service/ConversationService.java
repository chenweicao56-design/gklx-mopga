package com.gklx.mopga.admin.module.generate.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.dao.ConversationDao;
import com.gklx.mopga.admin.module.generate.domain.entity.ConversationEntity;
import com.gklx.mopga.admin.module.generate.domain.form.ConversationAddForm;
import com.gklx.mopga.admin.module.generate.domain.form.ConversationQueryForm;
import com.gklx.mopga.admin.module.generate.domain.form.ConversationUpdateForm;
import com.gklx.mopga.admin.module.generate.domain.vo.ConversationVo;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
import com.gklx.mopga.base.common.util.SmartPageUtil;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会话表 Service
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Service
public class ConversationService {

    @Resource
    private ConversationDao conversationDao;

    /**
     * 分页查询
     */
    public PageResult<ConversationVo> queryPage(ConversationQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<ConversationVo> list = conversationDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(ConversationAddForm addForm) {
        ConversationEntity conversationEntity = SmartBeanUtil.copy(addForm, ConversationEntity.class);
        conversationDao.insert(conversationEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(ConversationUpdateForm updateForm) {
        ConversationEntity conversationEntity = SmartBeanUtil.copy(updateForm, ConversationEntity.class);
        conversationDao.updateById(conversationEntity);
        return ResponseDTO.ok();
    }
    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }
        conversationDao.deleteByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }
        conversationDao.deleteById(id);
        return ResponseDTO.ok();
    }

}
