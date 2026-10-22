package com.gklx.mopga.admin.module.knowledge.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gklx.mopga.admin.module.knowledge.constant.KbDocumentStatusEnum;
import com.gklx.mopga.admin.module.knowledge.dao.KbDocumentDao;
import com.gklx.mopga.admin.module.knowledge.dao.KbSegmentDao;
import com.gklx.mopga.admin.module.knowledge.domain.entity.KbDocumentEntity;
import com.gklx.mopga.admin.module.knowledge.domain.entity.KbSegmentEntity;
import com.gklx.mopga.admin.module.knowledge.manager.KbSegmentProcessor;
import com.gklx.mopga.admin.module.knowledge.parser.KbFileParserFactory;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.module.support.file.domain.vo.FileDownloadVO;
import com.gklx.mopga.base.module.support.file.service.FileService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 知识库-解析/分段/向量化定时任务（F3 + F4）
 *
 * <p>轮询「待解析」文档，依次执行：文件解析 → 自动分段 → 向量化 → 标记已生效。</p>
 *
 * @author gklx
 */
@Slf4j
@Component
public class KbParseTask {

    @Resource
    private KbDocumentDao documentDao;

    @Resource
    private KbSegmentDao segmentDao;

    @Resource
    private FileService fileService;

    @Resource
    private KbFileParserFactory fileParserFactory;

    @Resource
    private KbSegmentProcessor segmentProcessor;

    @Resource
    private KbVectorService vectorService;

    @Value("${kb.schedule.parse-interval-ms:5000}")
    private long parseIntervalMs;

    @Value("${kb.schedule.max-parse-retry:3}")
    private int maxParseRetry;

    /**
     * 每次拉取处理的文档数
     */
    private static final int BATCH_SIZE = 5;

    /**
     * 轮询执行（固定间隔）
     */
    @Scheduled(fixedDelayString = "${kb.schedule.parse-interval-ms:5000}")
    public void poll() {
        List<KbDocumentEntity> pending = documentDao.selectList(
                new LambdaQueryWrapper<KbDocumentEntity>()
                        .eq(KbDocumentEntity::getStatus, KbDocumentStatusEnum.PENDING_PARSE.getValue())
                        .orderByAsc(KbDocumentEntity::getQueueOrder)
                        .last("LIMIT " + BATCH_SIZE));
        for (KbDocumentEntity doc : pending) {
            try {
                processOne(doc);
            } catch (Exception e) {
                log.error("文档处理失败: documentId={}, fileName={}", doc.getDocumentId(), doc.getFileName(), e);
                handleFailure(doc, e.getMessage());
            }
        }
    }

    /**
     * 处理单个文档：解析→分段→向量化
     */
    private void processOne(KbDocumentEntity doc) {
        // 1. 标记解析中
        markParsing(doc);

        // 2. 读取文件并解析
        byte[] data = readFile(doc.getFileUrl());
        if (data == null || data.length == 0) {
            throw new RuntimeException("文件内容为空或读取失败");
        }
        String parsedText = fileParserFactory.parse(data, doc.getFileName());
        if (StringUtils.isBlank(parsedText)) {
            throw new RuntimeException("解析结果为空，可能为扫描件或无文本内容");
        }
        doc.setParsedContent(parsedText);
        documentDao.updateById(doc);

        // 3. 自动分段
        List<String> segments = segmentProcessor.split(parsedText);
        doc.setStatus(KbDocumentStatusEnum.SEGMENTING.getValue());
        documentDao.updateById(doc);

        // 4. 向量化（调用内网 bge-m3）
        List<String> vectorIds = vectorService.batchVectorize(doc.getBaseId(), doc.getDocumentId(), segments);

        // 5. 落库分段 + 记录向量ID
        saveSegments(doc, segments, vectorIds);

        // 6. 标记已生效
        doc.setStatus(KbDocumentStatusEnum.EFFECTIVE.getValue());
        doc.setChunkCount(segments.size());
        doc.setErrorMessage(null);
        documentDao.updateById(doc);
        log.info("文档处理完成: documentId={}, fileName={}, segments={}",
                doc.getDocumentId(), doc.getFileName(), segments.size());
    }

    private void saveSegments(KbDocumentEntity doc, List<String> segments, List<String> vectorIds) {
        for (int i = 0; i < segments.size(); i++) {
            KbSegmentEntity seg = new KbSegmentEntity();
            seg.setDocumentId(doc.getDocumentId());
            seg.setBaseId(doc.getBaseId());
            seg.setSegmentIndex(i);
            seg.setContent(segments.get(i));
            seg.setCharacterCount(segments.get(i).length());
            if (i < vectorIds.size()) {
                seg.setVectorId(vectorIds.get(i));
            }
            seg.setStatus(1); // 已向量化
            segmentDao.insert(seg);
        }
    }

    private byte[] readFile(String fileKey) {
        if (StringUtils.isBlank(fileKey)) {
            return null;
        }
        ResponseDTO<FileDownloadVO> response = fileService.getDownloadFile(fileKey, "kb-parse-task");
        if (response == null || !response.getOk()) {
            return null;
        }
        FileDownloadVO vo = response.getData();
        return vo == null ? null : vo.getData();
    }

    private void markParsing(KbDocumentEntity doc) {
        doc.setStatus(KbDocumentStatusEnum.PARSING.getValue());
        documentDao.updateById(doc);
    }

    private void handleFailure(KbDocumentEntity doc, String message) {
        int retry = (doc.getParseRetryCount() == null ? 0 : doc.getParseRetryCount()) + 1;
        doc.setParseRetryCount(retry);
        if (retry >= maxParseRetry) {
            doc.setStatus(KbDocumentStatusEnum.PARSE_FAILED.getValue());
            doc.setErrorMessage(safeSubstring(message));
        } else {
            // 保留待解析状态，等待下次轮询重试
            doc.setStatus(KbDocumentStatusEnum.PENDING_PARSE.getValue());
            doc.setErrorMessage(safeSubstring(message));
        }
        documentDao.updateById(doc);
    }

    private String safeSubstring(String message) {
        if (message == null) {
            return "未知错误";
        }
        return message.length() > 500 ? message.substring(0, 500) : message;
    }
}
