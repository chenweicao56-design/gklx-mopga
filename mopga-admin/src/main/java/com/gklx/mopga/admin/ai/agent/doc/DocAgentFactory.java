package com.gklx.mopga.admin.ai.agent.doc;

import com.gklx.mopga.admin.ai.memory.CusChatMemoryStore;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocAgentFactory {

    @Resource
    private ContentRetriever docContentRetriever;
    @Resource
    private ChatModel chatModel;

    @Resource
    private StreamingChatModel streamingChatModel;


    @Resource
    private CusChatMemoryStore cusChatMemoryStore;

    @Bean
    public DocAgent docAgent() {
        return AiServices.builder(DocAgent.class)
                .chatModel(chatModel)
                .streamingChatModel(streamingChatModel)
                .contentRetriever(docContentRetriever)
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.builder().id(memoryId).maxMessages(10).chatMemoryStore(cusChatMemoryStore).build())
                .build();
    }
}
