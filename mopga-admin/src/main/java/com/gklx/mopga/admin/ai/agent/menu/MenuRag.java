package com.gklx.mopga.admin.ai.agent.menu;

import cn.hutool.json.JSONUtil;
import com.gklx.mopga.admin.module.system.menu.domain.vo.MenuTreeVO;
import com.gklx.mopga.admin.module.system.menu.domain.vo.MenuVO;
import com.gklx.mopga.admin.module.system.menu.service.MenuService;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.message.PdfFileContent;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static dev.langchain4j.store.embedding.chroma.ChromaApiVersion.V2;

@Configuration
public class MenuRag {

    @Resource
    private EmbeddingModel embeddingModel;

    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;

    @Resource
    private MenuService menuService;


    @Bean
    public ContentRetriever menuContentRetriever() {

//        EmbeddingStore<TextSegment> embeddingStore = ChromaEmbeddingStore.builder()
//                .apiVersion(V2)
//                .baseUrl("http://localhost:8080")
//                .collectionName("menu")
//                .logRequests(true)
//                .logResponses(true)
//                .build();


        ResponseDTO<List<MenuTreeVO>> listResponseDTO = menuService.queryMenuTree(true);
        List<MenuTreeVO> data = listResponseDTO.getData();
        List<Document> documents = data.stream()
                .map(e -> Document.from(JSONUtil.toJsonStr(e), Metadata.from("id", e.getMenuId().toString())))
                .toList();
        MenuDocumentSplitter menuDocumentSplitter = new MenuDocumentSplitter();

        // 3. 自定义文档加载器
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(menuDocumentSplitter)
                // 为了提高搜索质量，为每个 TextSegment 添加文档名称
                .textSegmentTransformer(textSegment -> TextSegment.from(
                        textSegment.text(),
                        textSegment.metadata()
                ))
                // 使用指定的向量模型
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        // 加载文档
        ingestor.ingest(documents);
        // 4. 自定义内容查询器
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(5) // 最多 5 个检索结果
                .build();
    }


}
