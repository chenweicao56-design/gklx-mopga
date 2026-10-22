package com.gklx.mopga.admin.module.knowledge.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.knowledge.constant.KbDocumentStatusEnum;
import com.gklx.mopga.admin.module.knowledge.dao.KbBaseDao;
import com.gklx.mopga.admin.module.knowledge.dao.KbDocumentDao;
import com.gklx.mopga.admin.module.knowledge.dao.KbSegmentDao;
import com.gklx.mopga.admin.module.knowledge.domain.entity.KbBaseEntity;
import com.gklx.mopga.admin.module.knowledge.domain.entity.KbDocumentEntity;
import com.gklx.mopga.admin.module.knowledge.domain.entity.KbSegmentEntity;
import com.gklx.mopga.admin.module.knowledge.domain.form.KbDocumentQueryForm;
import com.gklx.mopga.admin.module.knowledge.domain.form.KbDocumentRenameForm;
import com.gklx.mopga.admin.module.knowledge.domain.vo.KbDocumentVO;
import com.gklx.mopga.admin.module.knowledge.parser.KbFileParserFactory;
import com.gklx.mopga.base.common.domain.PageResult;
import com.gklx.mopga.base.common.domain.RequestUser;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.util.SmartBeanUtil;
import com.gklx.mopga.base.common.util.SmartPageUtil;
import com.gklx.mopga.base.common.util.SmartRequestUtil;
import com.gklx.mopga.base.module.support.file.constant.FileFolderTypeEnum;
import com.gklx.mopga.base.module.support.file.domain.vo.FileUploadVO;
import com.gklx.mopga.base.module.support.file.service.FileService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * 知识库-文档 Service（F2 上传/列表/重命名/删除）
 *
 * @author gklx
 */
@Slf4j
@Service
public class KbDocumentService {

    @Resource
    private KbDocumentDao documentDao;

    @Resource
    private KbBaseDao baseDao;

    @Resource
    private KbSegmentDao segmentDao;

    @Resource
    private FileService fileService;

    @Resource
    private KbFileParserFactory fileParserFactory;

    /**
     * 分页查询文档列表
     */
    public ResponseDTO<PageResult<KbDocumentVO>> queryPage(KbDocumentQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<KbDocumentVO> voList = documentDao.queryPage(page, queryForm);
        for (KbDocumentVO vo : voList) {
            fillVO(vo);
        }
        PageResult<KbDocumentVO> pageResult = SmartPageUtil.convert2PageResult(page, voList);
        return ResponseDTO.ok(pageResult);
    }

    /**
     * 上传文档
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> upload(Long baseId, MultipartFile file, RequestUser requestUser) {
        if (file == null || file.getSize() == 0) {
            return ResponseDTO.userErrorParam("上传文件不能为空");
        }
        KbBaseEntity base = baseDao.selectById(baseId);
        if (base == null) {
            return ResponseDTO.userErrorParam("知识库不存在");
        }
        String fileName = file.getOriginalFilename();
        String fileType = fileParserFactory.getFileType(fileName);
        if (!fileParserFactory.support(fileName)) {
            return ResponseDTO.userErrorParam("暂不支持的文件类型，仅支持 txt/md/pdf/docx/html");
        }
        // 走通用文件服务落盘
        ResponseDTO<FileUploadVO> uploadResponse = fileService.fileUpload(file, FileFolderTypeEnum.COMMON.getValue(), requestUser);
        if (!uploadResponse.getOk()) {
            return ResponseDTO.error(uploadResponse);
        }
        FileUploadVO uploadVO = uploadResponse.getData();

        // 记录文档
        KbDocumentEntity entity = new KbDocumentEntity();
        entity.setBaseId(baseId);
        entity.setFileName(fileName);
        entity.setFileUrl(uploadVO.getFileKey());
        entity.setFileSize(file.getSize());
        entity.setFileType(fileType);
        entity.setStatus(KbDocumentStatusEnum.PENDING_PARSE.getValue());
        entity.setQueueOrder(nextQueueOrder(baseId));
        entity.setChunkCount(0);
        entity.setParseRetryCount(0);
        entity.setCreateUserId(requestUser == null ? null : requestUser.getUserId());
        entity.setCreateUserName(requestUser == null ? null : requestUser.getUserName());
        documentDao.insert(entity);

        // 知识库文档数 +1
        base.setDocCount(base.getDocCount() == null ? 1 : base.getDocCount() + 1);
        baseDao.updateById(base);
        return ResponseDTO.ok();
    }

    /**
     * 重命名文档
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> rename(KbDocumentRenameForm renameForm) {
        KbDocumentEntity entity = documentDao.selectById(renameForm.getDocumentId());
        if (entity == null) {
            return ResponseDTO.userErrorParam("文档不存在");
        }
        String newName = renameForm.getFileName();
        String newType = fileParserFactory.getFileType(newName);
        if (!fileParserFactory.support(newName)) {
            return ResponseDTO.userErrorParam("暂不支持的文件类型，仅支持 txt/md/pdf/docx/html");
        }
        entity.setFileName(newName);
        entity.setFileType(newType);
        documentDao.updateById(entity);
        return ResponseDTO.ok();
    }

    /**
     * 删除文档（级联删分段与向量）
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long documentId) {
        KbDocumentEntity entity = documentDao.selectById(documentId);
        if (entity == null) {
            return ResponseDTO.userErrorParam("文档不存在");
        }
        // 删向量
        List<KbSegmentEntity> segments = segmentDao.selectList(
                new LambdaQueryWrapper<KbSegmentEntity>().eq(KbSegmentEntity::getDocumentId, documentId));
        List<String> vectorIds = segments.stream()
                .map(KbSegmentEntity::getVectorId)
                .filter(StringUtils::isNotBlank)
                .toList();
        if (!vectorIds.isEmpty()) {
            vectorService.deleteByVectorIds(vectorIds);
        }
        segmentDao.delete(new LambdaQueryWrapper<KbSegmentEntity>().eq(KbSegmentEntity::getDocumentId, documentId));
        documentDao.deleteById(documentId);

        // 知识库文档数 -1
        KbBaseEntity base = baseDao.selectById(entity.getBaseId());
        if (base != null && base.getDocCount() != null && base.getDocCount() > 0) {
            base.setDocCount(base.getDocCount() - 1);
            baseDao.updateById(base);
        }
        return ResponseDTO.ok();
    }

    /**
     * 触发重新解析（重置状态为待解析）
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> triggerParse(Long documentId) {
        KbDocumentEntity entity = documentDao.selectById(documentId);
        if (entity == null) {
            return ResponseDTO.userErrorParam("文档不存在");
        }
        // 删除旧的向量与分段
        List<KbSegmentEntity> segments = segmentDao.selectList(
                new LambdaQueryWrapper<KbSegmentEntity>().eq(KbSegmentEntity::getDocumentId, documentId));
        List<String> vectorIds = segments.stream()
                .map(KbSegmentEntity::getVectorId)
                .filter(StringUtils::isNotBlank)
                .toList();
        if (!vectorIds.isEmpty()) {
            vectorService.deleteByVectorIds(vectorIds);
        }
        segmentDao.delete(new LambdaQueryWrapper<KbSegmentEntity>().eq(KbSegmentEntity::getDocumentId, documentId));

        entity.setStatus(KbDocumentStatusEnum.PENDING_PARSE.getValue());
        entity.setQueueOrder(nextQueueOrder(entity.getBaseId()));
        entity.setChunkCount(0);
        entity.setParsedContent(null);
        entity.setErrorMessage(null);
        entity.setParseRetryCount(0);
        documentDao.updateById(entity);
        return ResponseDTO.ok();
    }

    /**
     * 取该知识库下最大队列序号
     */
    private int nextQueueOrder(Long baseId) {
        Integer max = documentDao.selectList(
                        new LambdaQueryWrapper<KbDocumentEntity>().eq(KbDocumentEntity::getBaseId, baseId))
                .stream().map(KbDocumentEntity::getQueueOrder)
                .filter(Objects::nonNull)
                .max(Integer::compareTo)
                .orElse(0);
        return max + 1;
    }

    private void fillVO(KbDocumentVO vo) {
        KbDocumentStatusEnum statusEnum = KbDocumentStatusEnum.getByValue(vo.getStatus());
        vo.setStatusDesc(statusEnum == null ? "未知" : statusEnum.getDesc());
        if (vo.getFileSize() != null) {
            vo.setFileSizeDesc(formatSize(vo.getFileSize()));
        }
    }

    private String formatSize(Long size) {
        if (size < 1024) {
            return size + "B";
        }
        if (size < 1024 * 1024) {
            return String.format("%.1fKB", size / 1024.0);
        }
        if (size < 1024L * 1024 * 1024) {
            return String.format("%.1fMB", size / (1024.0 * 1024));
        }
        return String.format("%.1fGB", size / (1024.0 * 1024 * 1024));
    }

    @Resource
    private KbVectorService vectorService;
}
