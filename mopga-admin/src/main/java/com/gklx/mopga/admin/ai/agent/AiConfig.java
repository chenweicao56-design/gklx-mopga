package com.gklx.mopga.admin.ai.agent;

import io.agentscope.core.model.DashScopeChatModel;
import io.agentscope.core.model.OpenAIChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {


    @Bean
    public OpenAIChatModel openAIChatModel() {
        return OpenAIChatModel.builder()
                .apiKey("sk-6c187d7368924e54a7d4cf76fe1430f0")
                .modelName("deepseek-v4-flash")
                .baseUrl("https://api.deepseek.com")
                .build();
    }
    @Bean
    public DashScopeChatModel dashScopeChatModel() {
        return DashScopeChatModel.builder()
                .apiKey("sk-bc047ae27a7c4b478f049886da1c15f0")
                .modelName("deepseek-v4-flash")
                .build();
    }
}
