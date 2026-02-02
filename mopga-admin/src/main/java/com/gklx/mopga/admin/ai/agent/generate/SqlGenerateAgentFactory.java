package com.gklx.mopga.admin.ai.agent.generate;

import com.gklx.mopga.admin.ai.agent.dict.DictAgent;
import com.gklx.mopga.admin.ai.agent.dict.DictTools;
import com.gklx.mopga.admin.ai.memory.CusChatMemoryStore;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SqlGenerateAgentFactory {


    @Resource
    private ChatModel chatModel;

    @Resource
    private StreamingChatModel streamingChatModel;

    @Resource
    private CusChatMemoryStore cusChatMemoryStore;

    @Bean(name = "sqlGenerateAgent")
    public SqlGenerateAgent sqlGenerateAgent() {

        return AiServices.builder(SqlGenerateAgent.class)
                .streamingChatModel(streamingChatModel)
                .build();
    }
}
