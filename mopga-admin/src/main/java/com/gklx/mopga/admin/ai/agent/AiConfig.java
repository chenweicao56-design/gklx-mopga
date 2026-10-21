package com.gklx.mopga.admin.ai.agent;

import com.gklx.mopga.admin.ai.mcp.RequirementMcp;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring AI 配置
 *
 * <p>聊天模型（{@link ChatModel}）通过 Spring AI 自动配置生成，
 * 见 application.yaml 的 spring.ai.deepseek 配置。</p>
 */
@Configuration
public class AiConfig {

    /**
     * 流式聊天客户端，供各智能体使用。
     */
    @Bean
    public ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }

    /**
     * 将 {@link RequirementMcp} 中的 {@link org.springframework.ai.tool.annotation.Tool}
     * 方法注册为可被智能体调用(CodeGenerateAgent)的工具。
     */
    @Bean
    public ToolCallbackProvider requirementMcpTools(RequirementMcp requirementMcp) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(requirementMcp)
                .build();
    }
}
