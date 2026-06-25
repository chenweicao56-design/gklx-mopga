package com.gklx.mopga.admin;

import cn.hutool.core.map.MapUtil;
import com.gklx.mopga.admin.ai.model.EmbeddingModel;
import com.gklx.mopga.admin.ai.rag.WeaviateTemplate;
import com.huaban.analysis.jieba.JiebaSegmenter;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import io.weaviate.client.Config;
import io.weaviate.client.WeaviateAuthClient;
import io.weaviate.client.WeaviateClient;
import io.weaviate.client.v1.auth.exception.AuthException;
import io.weaviate.client.v1.data.model.WeaviateObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class WeaviateTest {

    public static void main(String[] args) throws AuthException {
        JiebaSegmenter segmenter = new JiebaSegmenter();

        String className = "TestClass";
        EmbeddingModel embeddingModel = new EmbeddingModel();
        Config config = new Config("http", "10.167.104.116:8081");
        WeaviateClient weaviateClient = WeaviateAuthClient.apiKey(config, "WVF5YThaHlkYwhGUSmCRgsX3tD5ngdN8pkih");
        WeaviateTemplate weaviateTemplate = new WeaviateTemplate(weaviateClient);
        Map<String, List<String>> properties = MapUtil.<String, List<String>>builder()
                .put("content", List.of("text"))
                .put("keywords", List.of("text"))
                .build();

        if (!weaviateTemplate.exists(className)) weaviateTemplate.create(properties, className);


        Document pdfDocument = FileSystemDocumentLoader.loadDocument(
                "C:\\Users\\gklx\\Desktop\\曹晨伟简历.pdf",
                new ApachePdfBoxDocumentParser()
        );
        log.info("PDF 内容:{}", pdfDocument.text());
        DocumentSplitter recursiveSplitter = DocumentSplitters.recursive(512, 0);
        List<TextSegment> segments = recursiveSplitter.split(pdfDocument);

        List<Map<String, Object>> list = segments.stream().map(e ->
                MapUtil.<String, Object>builder()
                        .put("content", embeddingModel.embedding(e.text()))
                        .put("keywords", segmenter.)
                        .build()).toList();


        weaviateTemplate.batchSave(list, className);


        weaviateTemplate.truncate(className);

    }
}
