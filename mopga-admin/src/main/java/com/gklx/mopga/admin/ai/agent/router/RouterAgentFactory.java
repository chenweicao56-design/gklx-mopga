package com.gklx.mopga.admin.ai.agent.router;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterAgentFactory {

    @Resource
    private ChatModel chatModel;

    @Bean
    public RouterAgent routerAgent() {
        return AiServices.builder(RouterAgent.class)
                .chatModel(chatModel)
                .build();
    }
}
