package com.gklx.mopga.admin.ai.agent.dict;

import com.gklx.mopga.admin.ai.memory.CusChatMemoryStore;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DictAgentFactory {


    @Resource
    private ChatModel chatModel;

    @Resource
    private StreamingChatModel streamingChatModel;

    @Resource
    private CusChatMemoryStore cusChatMemoryStore;

    @Bean(name = "dictAgent")
    public DictAgent dictAgent() {

        return AiServices.builder(DictAgent.class)
                .streamingChatModel(streamingChatModel)
                .tools(new DictTools())
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(3)
                        .chatMemoryStore(cusChatMemoryStore)
                        .build())
                .build();
    }
}
