package com.gklx.mopga.admin.ai.agent.dict;

import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import com.gklx.mopga.admin.util.VelocityUtil;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.agent.tool.ToolSpecifications;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.request.ChatRequestParameters;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("dictAgentService")
public class DictAgentService extends BaseAgentService {

    public static final String AGENT_ALIAS = "dictAgent";

    @Resource
    private DictAgent dictAgent;
    @Resource
    private ChatModel chatModel;


    @Resource
    private StreamingChatModel streamingChatModel;

    @Override
    public void run(AgentContext agentContext) {
        DictTools tools = new DictTools();
        List<ToolSpecification> toolSpecifications = ToolSpecifications.toolSpecificationsFrom(tools);
        SystemMessage systemMessage = SystemMessage.from(VelocityUtil.text("prompt/dict/system.vm", Map.of()));
//        UserMessage userMessage = UserMessage.from(VelocityUtil.text("prompt/dict/user.vm", Map.of()));
        UserMessage userMessage = UserMessage.from(agentContext.getQuery());

        ChatRequest chatRequest = ChatRequest.builder()
                .messages(systemMessage, userMessage)
                .parameters(ChatRequestParameters.builder()
                        .toolSpecifications(toolSpecifications)
                        .build())
                .build();

        streamingChatModel.chat(chatRequest, new StreamingChatResponseHandler() {
            @Override
            public void onCompleteResponse(ChatResponse chatResponse) {

            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
//        ChatHandler handler = agentContext.getChatHandler();
//        TokenStream run = dictAgent.run(agentContext.getConversationId() + ":" + DictAgentService.AGENT_ALIAS, agentContext.getQuery());
//        run.onPartialResponse(message -> {
//                    handler.onAnswer(ChatResponse.builder().content(message).agentAlias(DictAgentService.AGENT_ALIAS).build());
//                }).onToolExecuted(message -> {
//                    agentContext.setAgentAlias(message.result());
//                    handler.onTransfer(agentContext);
//                    System.out.println(message);
//                })
//                .onCompleteResponse(s -> {
//                    if (agentContext.getAgentAlias().equals(DictAgentService.AGENT_ALIAS)) {
//                        handler.onComplete(ChatResponse.builder().agentAlias(DictAgentService.AGENT_ALIAS).build());
//                    }
//                }).onError(handler::onError).start();
    }
}
