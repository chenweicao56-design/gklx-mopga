package com.gklx.mopga.admin.module.generate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.domain.entity.ConversationEntity;
import com.gklx.mopga.admin.module.generate.domain.form.ConversationQueryForm;
import com.gklx.mopga.admin.module.generate.domain.vo.ConversationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会话表 Dao
 *
 * @Author gklx
 * @Date 2025-09-06 18:37:05
 * @Copyright 1.0
 */

@Mapper
public interface ConversationDao extends BaseMapper<ConversationEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<ConversationVo> queryPage(Page page, @Param("queryForm") ConversationQueryForm queryForm);

}
