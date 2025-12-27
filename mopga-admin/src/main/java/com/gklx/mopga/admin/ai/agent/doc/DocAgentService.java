package com.gklx.mopga.admin.ai.agent.doc;

import com.gklx.mopga.admin.ai.core.ChatHandler;
import com.gklx.mopga.admin.ai.core.ChatResponse;
import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import dev.langchain4j.service.TokenStream;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("docAgentService")
public class DocAgentService extends BaseAgentService {

    public static final String AGENT_ALIAS = "docAgent";

    @Resource
    private DocAgent docAgent;


    @Override
    public void run(AgentContext agentContext) {
        ChatHandler handler = agentContext.getChatHandler();
        TokenStream run = docAgent.run(agentContext.getConversationId() + "doc", agentContext.getQuery());
        run.onPartialResponse(message -> {
            handler.onAnswer(ChatResponse.builder().content(message).agentAlias(DocAgentService.AGENT_ALIAS).build());
        }).onCompleteResponse(s -> {
            handler.onComplete(ChatResponse.builder().agentAlias(DocAgentService.AGENT_ALIAS).build());
        }).onError(handler::onError)
                .onRetrieved(s ->{
                   s.forEach(System.out::println);
                }).start();
    }
}
