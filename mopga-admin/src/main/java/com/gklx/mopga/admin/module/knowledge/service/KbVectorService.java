package com.gklx.mopga.admin.module.knowledge.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.weaviate.WeaviateVectorStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 知识库-向量化服务（Weaviate 读写）
 *
 * <p>依赖 Spring AI WeaviateVectorStore + kbEmbeddingModel（内网 bge-m3）。</p>
 *
 * @author gklx
 */
@Slf4j
@Service
public class KbVectorService {

    private final WeaviateVectorStore vectorStore;

    public KbVectorService(WeaviateVectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    /**
     * 将分段批量写入向量库
     *
     * @param baseId     知识库ID
     * @param documentId 文档ID
     * @param contents   分段内容（有序）
     * @return 与 contents 一一对应的向量对象ID（Weaviate UUID）
     */
    public List<String> batchVectorize(Long baseId, Long documentId, List<String> contents) {
        if (contents == null || contents.isEmpty()) {
            return new ArrayList<>();
        }
        List<Document> documents = new ArrayList<>(contents.size());
        List<String> vectorIds = new ArrayList<>(contents.size());
        int index = 0;
        for (String content : contents) {
            String vectorId = UUID.randomUUID().toString();
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("baseId", String.valueOf(baseId));
            metadata.put("documentId", String.valueOf(documentId));
            metadata.put("segmentIndex", index);
            documents.add(Document.builder()
                    .id(vectorId)
                    .text(content)
                    .metadata(metadata)
                    .build());
            vectorIds.add(vectorId);
            index++;
        }
        vectorStore.add(documents);
        log.info("向量化完成: baseId={}, documentId={}, count={}", baseId, documentId, documents.size());
        return vectorIds;
    }

    /**
     * 按向量对象ID删除
     */
    public void deleteByVectorIds(List<String> vectorIds) {
        if (vectorIds == null || vectorIds.isEmpty()) {
            return;
        }
        vectorStore.delete(vectorIds);
        log.info("删除向量: count={}", vectorIds.size());
    }
}
