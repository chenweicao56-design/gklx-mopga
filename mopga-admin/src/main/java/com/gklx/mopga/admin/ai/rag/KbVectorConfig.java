package com.gklx.mopga.admin.ai.rag;

import com.gklx.mopga.admin.ai.model.EmbeddingModel;
import io.weaviate.client.Config;
import io.weaviate.client.WeaviateClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.weaviate.WeaviateVectorStore;
import org.springframework.ai.vectorstore.weaviate.WeaviateVectorStoreOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 知识库-向量检索基础设施
 *
 * <p>基于 Spring AI + Weaviate（docker-compose 已提供 weaviate 服务），
 * 配合自建 EmbeddingModel（内网 bge-m3 接口）实现分段的向量写入与检索。</p>
 *
 * @author gklx
 */
@Slf4j
@Configuration
public class KbVectorConfig {

    @Value("${kb.weaviate.scheme:http}")
    private String scheme;

    @Value("${kb.weaviate.host:127.0.0.1:8099}")
    private String host;

    /**
     * Weaviate 客户端（原生客户端）
     */
    @Bean
    public WeaviateClient weaviateClient() {
        Config config = new Config(scheme, host);
        WeaviateClient client = new WeaviateClient(config);
        log.info("初始化 WeaviateClient: {}://{}", scheme, host);
        return client;
    }

    /**
     * 自建 EmbeddingModel（内网 bge-m3 接口）
     */
    @Bean
    public EmbeddingModel kbEmbeddingDelegate() {
        return new EmbeddingModel();
    }

    /**
     * Spring AI EmbeddingModel 适配器（包装自建 bge-m3 调用）
     */
    @Bean
    public org.springframework.ai.embedding.EmbeddingModel kbEmbeddingModel(EmbeddingModel kbEmbeddingDelegate) {
        return new org.springframework.ai.embedding.EmbeddingModel() {

            @Override
            public EmbeddingResponse call(EmbeddingRequest request) {
                List<String> instructions = request.getInstructions();
                List<Embedding> embeddings = new ArrayList<>(instructions.size());
                Float[][] vectors = kbEmbeddingDelegate.embedding(instructions);
                for (int i = 0; i < vectors.length; i++) {
                    Float[] vec = vectors[i];
                    float[] floats = new float[vec.length];
                    for (int j = 0; j < vec.length; j++) {
                        floats[j] = vec[j];
                    }
                    embeddings.add(new Embedding(floats, i));
                }
                return new EmbeddingResponse(embeddings);
            }

            @Override
            public float[] embed(org.springframework.ai.document.Document document) {
                Float[] vec = kbEmbeddingDelegate.embedding(document.getFormattedContent());
                float[] floats = new float[vec.length];
                for (int j = 0; j < vec.length; j++) {
                    floats[j] = vec[j];
                }
                return floats;
            }

            @Override
            public int dimensions() {
                return 1024;
            }
        };
    }

    /**
     * Weaviate 向量库（分段向量读写）
     */
    @Bean
    public WeaviateVectorStore kbVectorStore(WeaviateClient weaviateClient,
                                             org.springframework.ai.embedding.EmbeddingModel kbEmbeddingModel) {
        WeaviateVectorStoreOptions options = new WeaviateVectorStoreOptions();
        options.setObjectClass("KbSegment");
        options.setContentFieldName("content");
        return WeaviateVectorStore.builder(weaviateClient, kbEmbeddingModel)
                .options(options)
                .build();
    }
}
