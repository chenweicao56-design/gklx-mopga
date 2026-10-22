package com.gklx.mopga.admin.module.knowledge.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gklx.mopga.admin.module.knowledge.domain.entity.KbDocumentEntity;
import com.gklx.mopga.admin.module.knowledge.domain.form.KbDocumentQueryForm;
import com.gklx.mopga.admin.module.knowledge.domain.vo.KbDocumentVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 知识库-文档 Dao
 *
 * @author gklx
 */
@Mapper
public interface KbDocumentDao extends BaseMapper<KbDocumentEntity> {

    /**
     * 分页查询文档列表
     */
    List<KbDocumentVO> queryPage(Page<?> page, @Param("queryForm") KbDocumentQueryForm queryForm);

    /**
     * 统计知识库下文档数量
     */
    Long countByBaseId(@Param("baseId") Long baseId);
}
