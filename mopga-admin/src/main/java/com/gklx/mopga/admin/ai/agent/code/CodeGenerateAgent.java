package com.gklx.mopga.admin.ai.agent.code;

import com.gklx.mopga.admin.ai.core.ChatHandler;
import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service("CodeGenerateAgent")
public class CodeGenerateAgent extends BaseAgentService {

    @Resource
    private ChatClient chatClient;

    @Resource
    private ToolCallbackProvider toolCallbackProvider;

    @Override
    public void run(AgentContext agentContext) {
        ChatHandler chatHandler = agentContext.getChatHandler();
        try {
            Flux<String> stream = chatClient.prompt()
                    .system("你是一个有帮助的代码生成助手，可以根据需要调用工具查询数据库、建表和生成代码。")
                    .user(agentContext.getQuery())
                    .tools(toolCallbackProvider)
                    .stream()
                    .content();
            agentContext.subscribe(stream);
        } catch (Exception e) {
            chatHandler.onError(e);
        }
    }
}
