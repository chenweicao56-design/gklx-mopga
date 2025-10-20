package com.gklx.mopga.admin.ai.rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * 加载 RAG
 */
//@Configuration
public class RagConfig {

    @Resource
    private EmbeddingModel embeddingModel;

    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;

    @Bean
    public ContentRetriever contentRetriever() throws URISyntaxException {
        // ------ RAG ------
        // 1. 加载文档
        ClassLoader classLoader = getClass().getClassLoader();
        URL resourceUrl = classLoader.getResource("docs");
        File docsDir = new File(resourceUrl.toURI());
        List<Document> documents = FileSystemDocumentLoader.loadDocuments(docsDir.toPath());
        // 2. 文档切割：将每个文档按每段进行分割，最大 1000 字符，每次重叠最多 200 个字符
        DocumentByParagraphSplitter paragraphSplitter = new DocumentByParagraphSplitter(1000, 200);
        // 3. 自定义文档加载器
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(paragraphSplitter)
                // 为了提高搜索质量，为每个 TextSegment 添加文档名称
                .textSegmentTransformer(textSegment -> TextSegment.from(
                        textSegment.metadata().getString("file_name") + "\n" + textSegment.text(),
                        textSegment.metadata()
                ))
                // 使用指定的向量模型
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        // 加载文档
        ingestor.ingest(documents);
        // 4. 自定义内容查询器
        ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(5) // 最多 5 个检索结果
                .minScore(0.75) // 过滤掉分数小于 0.75 的结果
                .build();
        return contentRetriever;
    }
}
