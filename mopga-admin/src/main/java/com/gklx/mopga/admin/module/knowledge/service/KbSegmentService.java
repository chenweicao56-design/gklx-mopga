package com.gklx.mopga.admin.module.knowledge.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.knowledge.dao.KbDocumentDao;
import com.gklx.mopga.admin.module.knowledge.dao.KbSegmentDao;
import com.gklx.mopga.admin.module.knowledge.domain.entity.KbDocumentEntity;
import com.gklx.mopga.admin.module.knowledge.domain.form.KbSegmentQueryForm;
import com.gklx.mopga.admin.module.knowledge.domain.vo.KbSegmentVO;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.util.SmartPageUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 知识库-分段 Service（分段列表只读展示）
 *
 * @author gklx
 */
@Slf4j
@Service
public class KbSegmentService {

    @Resource
    private KbSegmentDao segmentDao;

    @Resource
    private KbDocumentDao documentDao;

    /**
     * 分页查询某文档的分段列表
     */
    public ResponseDTO<PageResult<KbSegmentVO>> queryPage(KbSegmentQueryForm queryForm) {
        KbDocumentEntity doc = documentDao.selectById(queryForm.getDocumentId());
        if (doc == null) {
            return ResponseDTO.userErrorParam("文档不存在");
        }
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<KbSegmentVO> voList = segmentDao.queryPage(page, queryForm.getDocumentId());
        for (KbSegmentVO vo : voList) {
            vo.setStatusDesc(vo.getStatus() != null && vo.getStatus() == 1 ? "已向量化"
                    : (vo.getStatus() != null && vo.getStatus() == 2 ? "向量化失败" : "未向量化"));
        }
        return ResponseDTO.ok(SmartPageUtil.convert2PageResult(page, voList));
    }
}
