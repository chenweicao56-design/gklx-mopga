package com.gklx.mopga.admin.module.knowledge.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gklx.mopga.admin.module.knowledge.domain.entity.KbBaseEntity;
import com.gklx.mopga.admin.module.knowledge.domain.form.KbBaseQueryForm;
import com.gklx.mopga.admin.module.knowledge.domain.vo.KbBaseVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 知识库-知识库 Dao
 *
 * @author gklx
 */
@Mapper
public interface KbBaseDao extends BaseMapper<KbBaseEntity> {

    /**
     * 分页查询知识库列表
     */
    List<KbBaseVO> queryPage(Page<?> page, @Param("queryForm") KbBaseQueryForm queryForm);

    /**
     * 校验知识库名称是否重复
     */
    KbBaseEntity queryByName(@Param("name") String name, @Param("excludeBaseId") Long excludeBaseId);
}
