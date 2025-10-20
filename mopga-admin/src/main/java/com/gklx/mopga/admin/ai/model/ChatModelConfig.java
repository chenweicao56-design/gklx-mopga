package com.gklx.mopga.admin.ai.model;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.http.client.spring.restclient.SpringRestClientBuilderFactory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2q.AllMiniLmL6V2QuantizedEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatModelConfig {

    @Value("${langchain4j.community.dashscope.chat-model.model-name}")
    public String modelName;
    @Value("${langchain4j.community.dashscope.chat-model.api-key}")
    private String apiKey;
    @Value("${langchain4j.community.dashscope.chat-model.base-url}")
    private String baseUrl;

    @Resource
    private ChatModelListener chatModelListener;

    @Bean
    public ChatModel chatModel() {
        SpringRestClientBuilderFactory httpClientFactory = new SpringRestClientBuilderFactory();
        return OpenAiChatModel.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .modelName(modelName)
                .httpClientBuilder(httpClientFactory.create())
                .listeners(List.of(chatModelListener))
                .build();
    }

    @Bean
    public OpenAiStreamingChatModel streamingChatModel() {
        SpringRestClientBuilderFactory httpClientFactory = new SpringRestClientBuilderFactory();
        return OpenAiStreamingChatModel.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .modelName(modelName)
                .httpClientBuilder(httpClientFactory.create())
                .listeners(List.of(chatModelListener))
                .build();
    }

    @Bean
    public EmbeddingModel embeddingModel() {
       return  new AllMiniLmL6V2QuantizedEmbeddingModel();

    }

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
       return new InMemoryEmbeddingStore<>();
    }
}
