package com.gklx.mopga.admin.module.knowledge.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.knowledge.dao.KbBaseDao;
import com.gklx.mopga.admin.module.knowledge.dao.KbDocumentDao;
import com.gklx.mopga.admin.module.knowledge.domain.entity.KbBaseEntity;
import com.gklx.mopga.admin.module.knowledge.domain.entity.KbDocumentEntity;
import com.gklx.mopga.admin.module.knowledge.domain.entity.KbSegmentEntity;
import com.gklx.mopga.admin.module.knowledge.domain.form.KbBaseAddForm;
import com.gklx.mopga.admin.module.knowledge.domain.form.KbBaseQueryForm;
import com.gklx.mopga.admin.module.knowledge.domain.form.KbBaseUpdateForm;
import com.gklx.mopga.admin.module.knowledge.domain.vo.KbBaseVO;
import com.gklx.mopga.admin.module.knowledge.dao.KbSegmentDao;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
import com.gklx.mopga.base.common.util.SmartPageUtil;
import com.gklx.mopga.base.common.util.SmartRequestUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 知识库-知识库 Service（F1）
 *
 * @author gklx
 */
@Slf4j
@Service
public class KbBaseService {

    @Resource
    private KbBaseDao baseDao;

    @Resource
    private KbDocumentDao documentDao;

    @Resource
    private KbSegmentDao segmentDao;

    @Resource
    private KbVectorService vectorService;

    /**
     * 分页查询知识库列表
     */
    public ResponseDTO<PageResult<KbBaseVO>> queryPage(KbBaseQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<KbBaseVO> voList = baseDao.queryPage(page, queryForm);
        PageResult<KbBaseVO> pageResult = SmartPageUtil.convert2PageResult(page, voList);
        return ResponseDTO.ok(pageResult);
    }

    /**
     * 查询全部知识库（下拉用）
     */
    public ResponseDTO<List<KbBaseVO>> queryAll() {
        List<KbBaseEntity> entityList = baseDao.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<KbBaseEntity>()
                        .orderByDesc(KbBaseEntity::getCreateTime));
        List<KbBaseVO> voList = entityList.stream()
                .map(e -> SmartBeanUtil.copy(e, KbBaseVO.class))
                .toList();
        return ResponseDTO.ok(voList);
    }

    /**
     * 新建知识库
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> create(KbBaseAddForm addForm) {
        KbBaseEntity exist = baseDao.queryByName(addForm.getName(), null);
        if (exist != null) {
            return ResponseDTO.userErrorParam("已存在同名知识库");
        }
        KbBaseEntity entity = SmartBeanUtil.copy(addForm, KbBaseEntity.class);
        entity.setDocCount(0);
        entity.setCreateUserId(SmartRequestUtil.getRequestUserId());
        entity.setCreateUserName(SmartRequestUtil.getRequestUser() == null ? null : SmartRequestUtil.getRequestUser().getUserName());
        baseDao.insert(entity);
        return ResponseDTO.ok();
    }

    /**
     * 编辑知识库
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(KbBaseUpdateForm updateForm) {
        KbBaseEntity entity = baseDao.selectById(updateForm.getBaseId());
        if (entity == null) {
            return ResponseDTO.userErrorParam("知识库不存在");
        }
        KbBaseEntity exist = baseDao.queryByName(updateForm.getName(), updateForm.getBaseId());
        if (exist != null) {
            return ResponseDTO.userErrorParam("已存在同名知识库");
        }
        entity.setName(updateForm.getName());
        entity.setDescription(updateForm.getDescription());
        baseDao.updateById(entity);
        return ResponseDTO.ok();
    }

    /**
     * 删除知识库（级联删除文档、分段、向量）
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long baseId) {
        KbBaseEntity entity = baseDao.selectById(baseId);
        if (entity == null) {
            return ResponseDTO.userErrorParam("知识库不存在");
        }
        // 查询知识库下所有文档与分段
        List<KbDocumentEntity> docs = documentDao.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<KbDocumentEntity>()
                        .eq(KbDocumentEntity::getBaseId, baseId));
        List<Long> docIds = docs.stream().map(KbDocumentEntity::getDocumentId).toList();
        List<String> vectorIds = new java.util.ArrayList<>();
        if (!docIds.isEmpty()) {
            List<KbSegmentEntity> segments = segmentDao.selectList(
                    new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<KbSegmentEntity>()
                            .in(KbSegmentEntity::getDocumentId, docIds));
            for (KbSegmentEntity seg : segments) {
                if (seg.getVectorId() != null) {
                    vectorIds.add(seg.getVectorId());
                }
            }
            // 删除向量
            vectorService.deleteByVectorIds(vectorIds);
            // 删除分段与文档
            segmentDao.delete(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<KbSegmentEntity>()
                    .in(KbSegmentEntity::getDocumentId, docIds));
            documentDao.deleteBatchIds(docIds);
        }
        baseDao.deleteById(baseId);
        return ResponseDTO.ok();
    }
}
