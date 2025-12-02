package com.gklx.mopga.admin.ai.agent.dict;

import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.core.ChatResponse;
import com.gklx.mopga.admin.ai.core.ChatHandler;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import dev.langchain4j.service.TokenStream;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("dictAgentService")
public class DictAgentService extends BaseAgentService {

    public static final String AGENT_ALIAS = "dictAgent";

    @Resource
    private DictAgent dictAgent;


    @Override
    public void run(AgentContext agentContext) {
        ChatHandler handler = agentContext.getChatHandler();
        TokenStream run = dictAgent.run(agentContext.getConversationId() + ":" + DictAgentService.AGENT_ALIAS, agentContext.getQuery());
        run.onPartialResponse(message -> {
            handler.onAnswer(ChatResponse.builder().content(message).agentAlias(DictAgentService.AGENT_ALIAS).build());
        }).onCompleteResponse(s -> {
            handler.onComplete(ChatResponse.builder().agentAlias(DictAgentService.AGENT_ALIAS).build());
        }).onError(handler::onError).start();
    }
}
