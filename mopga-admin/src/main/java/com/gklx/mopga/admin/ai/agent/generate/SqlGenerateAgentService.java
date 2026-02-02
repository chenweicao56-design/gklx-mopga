package com.gklx.mopga.admin.ai.agent.generate;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gklx.mopga.admin.ai.core.ChatHandler;
import com.gklx.mopga.admin.ai.core.ChatResponse;
import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import dev.langchain4j.service.TokenStream;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("sqlGenerateAgentService")
public class SqlGenerateAgentService extends BaseAgentService {

    public static final String AGENT_ALIAS = "SqlGenerateAgentService";

    @Resource
    private SqlGenerateAgent sqlGenerateAgent;

    @Override
    public void run(AgentContext agentContext) {

        ChatHandler handler = agentContext.getChatHandler();
        JSONObject data = agentContext.getData();
        TokenStream run = sqlGenerateAgent.run(agentContext.getQuery(), JSONUtil.toJsonStr(data));
        run.onPartialResponse(message -> {
                    handler.onAnswer(com.gklx.mopga.admin.ai.core.ChatResponse.builder().content(message).agentAlias(AGENT_ALIAS).build());
                }).onCompleteResponse(s -> {
                    handler.onComplete(ChatResponse.builder().agentAlias(AGENT_ALIAS).build());
                }).onError(handler::onError)
                .onRetrieved(s -> {
                    s.forEach(System.out::println);
                }).start();
    }
}
