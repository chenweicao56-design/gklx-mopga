package com.gklx.mopga.admin.ai.agent.menu;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuAgentFactory {

    @Resource
    private ContentRetriever menuContentRetriever;
    @Resource
    private ChatModel chatModel;

    @Resource
    private StreamingChatModel streamingChatModel;


    @Bean(name = "menuAgent")
    public menuAgent menuAgent() {

        return AiServices.builder(menuAgent.class)
                .chatModel(chatModel)
                .streamingChatModel(streamingChatModel)
                .contentRetriever(menuContentRetriever) // RAG 检索增强生成
                .build();
    }
}
