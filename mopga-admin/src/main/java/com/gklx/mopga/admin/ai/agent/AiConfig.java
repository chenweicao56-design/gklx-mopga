package com.gklx.mopga.admin.ai.agent;

import io.agentscope.core.model.OpenAIChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {


    @Bean
    public OpenAIChatModel openAIChatModel() {
        return OpenAIChatModel.builder()
                .apiKey("sk-87c6cc6fb84440aca57f17305a6d69de")
                .modelName("deepseek-chat")
                .baseUrl("https://api.deepseek.com")
                .build();
    }
}
