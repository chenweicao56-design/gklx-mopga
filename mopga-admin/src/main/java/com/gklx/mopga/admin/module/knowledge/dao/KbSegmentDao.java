package com.gklx.mopga.admin.module.knowledge.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gklx.mopga.admin.module.knowledge.domain.entity.KbSegmentEntity;
import com.gklx.mopga.admin.module.knowledge.domain.vo.KbSegmentVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 知识库-分段 Dao
 *
 * @author gklx
 */
@Mapper
public interface KbSegmentDao extends BaseMapper<KbSegmentEntity> {

    /**
     * 分页查询分段列表
     */
    List<KbSegmentVO> queryPage(Page<?> page, @Param("documentId") Long documentId);
}
