package com.gklx.mopga.admin.ai.agent.manager;

import com.gklx.mopga.admin.ai.memory.CusChatMemoryStore;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ManagerAgentFactory {

    @Resource
    private ChatModel chatModel;

    @Resource
    private CusChatMemoryStore cusChatMemoryStore;

    @Bean
    public ManagerAgent managerAgent() {
        return AiServices.builder(ManagerAgent.class)
                .chatModel(chatModel)
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(10)
                        .chatMemoryStore(cusChatMemoryStore)
                        .build())
                .build();
    }
}
