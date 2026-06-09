package com.gklx.mopga.admin.ai.agent;


import cn.hutool.core.map.MapUtil;
import com.gklx.ai.util.FreemarkerUtil;
import com.gklx.mopga.admin.ai.core.ChatHandler;
import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import io.agentscope.core.event.AgentEvent;
import io.agentscope.core.message.UserMessage;
import io.agentscope.core.model.OpenAIChatModel;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service("HarnessAgent")
public class HarnessAgent extends BaseAgentService {
    @Resource
    private OpenAIChatModel openAIChatModel;

    @Override
    public void run(AgentContext agentContext) {
        ChatHandler chatHandler = agentContext.getChatHandler();
        Map<String, Object> systemParams = MapUtil.<String, Object>builder().build();

        try (io.agentscope.harness.agent.HarnessAgent agent = io.agentscope.harness.agent.HarnessAgent.builder()
                .model(openAIChatModel)
                .sysPrompt(FreemarkerUtil.render("harness/system.md", systemParams))
                .build()) {
            Flux<AgentEvent> agentEventFlux = agent.streamEvents(new UserMessage(agentContext.getQuery()));
            agentContext.subscribe(agentEventFlux);
        } catch (Exception e) {
            chatHandler.onError(e);
        }
    }
}
