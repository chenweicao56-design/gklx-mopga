package com.gklx.mopga.admin.ai.agent.doc;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DocRag {

    @Bean
    public EmbeddingStore<TextSegment> docEmbeddingStore() {
        return new InMemoryEmbeddingStore<>();
    }

    @Bean
    public ContentRetriever docContentRetriever(EmbeddingStore<TextSegment> docEmbeddingStore, EmbeddingModel embeddingModel) {
        List<Document> documents = ClassPathDocumentLoader.loadDocuments("docs/doc/md", new TextDocumentParser());
        MarkdownDocumentSplitter paragraphSplitter = new MarkdownDocumentSplitter();
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(paragraphSplitter)
                .textSegmentTransformer(textSegment -> TextSegment.from(
                        textSegment.text(),
                        textSegment.metadata()
                ))
                .embeddingModel(embeddingModel)
                .embeddingStore(docEmbeddingStore)
                .build();
        // 加载文档
        ingestor.ingest(documents);
        EmbeddingStoreContentRetriever build = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(docEmbeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(5) // 最多 5 个检索结果
                .minScore(0.75) // 过滤掉分数小于 0.75 的结果
                .build();
        return build;
    }
}
