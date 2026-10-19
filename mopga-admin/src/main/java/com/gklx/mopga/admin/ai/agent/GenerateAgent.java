package com.gklx.mopga.admin.ai.agent;


import com.gklx.mopga.admin.ai.core.ChatHandler;
import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service("GenerateAgent")
public class GenerateAgent extends BaseAgentService {
    @Resource
    private ChatModel chatModel;

    @Override
    public void run(AgentContext agentContext) {
        ChatHandler chatHandler = agentContext.getChatHandler();
        try {
            List<Message> messages = new ArrayList<>();
            messages.add(new SystemMessage("你是一个有帮助的智能助手。"));
            messages.add(new UserMessage(agentContext.getQuery()));

            Prompt prompt = new Prompt(messages);
            Flux<String> stream = chatModel.stream(prompt)
                    .map(r -> r.getResult().getOutput().getText());
            agentContext.subscribe(stream);
        } catch (Exception e) {
            chatHandler.onError(e);
        }
    }
}
