package com.gklx.mopga.admin.ai.serivce;

import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FrontCustomComponentServiceFactory {

    @Resource
    private StreamingChatModel streamingChatModel;

    @Bean
    public FrontCustomComponentService frontCustomComponentService() {
        return AiServices.builder(FrontCustomComponentService.class)
                .streamingChatModel(streamingChatModel)
                .build();
    }
}
