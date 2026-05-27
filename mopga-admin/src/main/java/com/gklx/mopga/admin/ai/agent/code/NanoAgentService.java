package com.gklx.mopga.admin.ai.agent.code;

import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import com.gklx.mopga.admin.util.AgentUtil;
import io.agentscope.core.ReActAgent;
import io.agentscope.core.agent.Event;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service("nanoAgentService")
public class NanoAgentService extends BaseAgentService {

    @Resource
    private AgentFactory agentFactory;

    @Override
    public void run(AgentContext agentContext) {
        try {
            ReActAgent agent = agentFactory.getAgent(agentContext.getConversationId());
            Flux<Event> stream = agent.stream(agentContext.defaultUserMsg(), AgentUtil.getCommonStreamOptions());
            agentContext.subscribe(stream);
        } catch (Exception e) {
            agentContext.getChatHandler().onError(e);
        }
    }
}
