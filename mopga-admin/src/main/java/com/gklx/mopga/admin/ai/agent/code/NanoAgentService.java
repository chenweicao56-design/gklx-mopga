package com.gklx.mopga.admin.ai.agent.code;

import com.gklx.mopga.admin.ai.core.ChatHandler;
import com.gklx.mopga.admin.ai.core.ChatResponse;
import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import io.agentscope.core.ReActAgent;
import io.agentscope.core.agent.Event;
import io.agentscope.core.agent.EventType;
import io.agentscope.core.agent.StreamOptions;
import io.agentscope.core.message.ContentBlock;
import io.agentscope.core.message.Msg;
import io.agentscope.core.message.TextBlock;
import io.agentscope.core.message.ThinkingBlock;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service("nanoAgentService")
public class NanoAgentService extends BaseAgentService {

    public static final String AGENT_ALIAS = "nanoAgentService";

    @Resource
    private AgentFactory agentFactory;

    @Override
    public void run(AgentContext agentContext) {
        try {
            ChatHandler handler = agentContext.getChatHandler();
            ReActAgent agent = agentFactory.getAgent(agentContext.getConversationId());

            Msg userMsg = Msg.builder()
                    .textContent(agentContext.getQuery())
                    .build();
            StreamOptions streamOptions =
                    StreamOptions.builder()
                            .eventTypes(EventType.REASONING, EventType.TOOL_RESULT)
                            .incremental(true)
                            .includeReasoningResult(false)
                            .build();
            Flux<Event> stream = agent.stream(userMsg, streamOptions);

            stream.subscribe(event -> {
                Msg msg = event.getMessage();
                for (ContentBlock block : msg.getContent()) {
                    if (block instanceof ThinkingBlock) {
                    } else if (block instanceof TextBlock) {
                        handler.onAnswer(ChatResponse.builder().content(((TextBlock) block).getText()).agentAlias(AGENT_ALIAS).build());
                    }
                }
            }, handler::onError, () -> {
                handler.onComplete(com.gklx.mopga.admin.ai.core.ChatResponse.builder().agentAlias(AGENT_ALIAS).build());
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
