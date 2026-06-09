package com.gklx.mopga.admin.ai.agent.code;

import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import io.agentscope.core.ReActAgent;
import io.agentscope.core.event.AgentEvent;
import io.agentscope.core.message.UserMessage;
import io.agentscope.core.model.OpenAIChatModel;
import io.agentscope.core.skill.repository.ClasspathSkillRepository;
import io.agentscope.core.tool.Toolkit;
import io.agentscope.harness.agent.HarnessAgent;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;

@Service("CodeGenerateAgent")
public class CodeGenerateAgent extends BaseAgentService {

    @Resource
    private OpenAIChatModel openAIChatModel;


    @Override
    public void run(AgentContext agentContext) {
        Toolkit toolkit = new Toolkit();
        toolkit.registerTool(new io.agentscope.core.tool.builtin.TodoTools());
        HarnessAgent agent = HarnessAgent.builder()
                .model(openAIChatModel)
                .sysPrompt("你是一个有帮助的助手。")
                .build();

        Flux<AgentEvent> agentEventFlux = agent.streamEvents(new UserMessage(agentContext.getQuery()));
        agentContext.subscribe(agentEventFlux);

    }
}
