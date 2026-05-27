package com.gklx.mopga.admin.ai.domain;

import cn.hutool.json.JSONObject;
import com.gklx.mopga.admin.ai.core.ChatHandler;
import com.gklx.mopga.admin.ai.core.ChatResponse;
import io.agentscope.core.agent.Event;
import io.agentscope.core.message.ContentBlock;
import io.agentscope.core.message.Msg;
import io.agentscope.core.message.TextBlock;
import io.agentscope.core.message.ThinkingBlock;
import lombok.Data;
import reactor.core.publisher.Flux;

import java.util.List;


@Data
public class AgentContext {

    private String conversationId;

    private String query;

    private String agentAlias;

    private String userId;

    private ChatHandler chatHandler;

    private JSONObject data;

    private List<String> files;

    public Msg defaultUserMsg() {
        return Msg.builder()
                .textContent(this.getQuery())
                .build();
    }

    public void subscribe(Flux<Event> stream) {
        stream.subscribe(event -> {
            Msg msg = event.getMessage();
            for (ContentBlock block : msg.getContent()) {
                if (block instanceof ThinkingBlock) {
                } else if (block instanceof TextBlock) {
                    this.chatHandler.onAnswer(ChatResponse.builder().content(((TextBlock) block).getText()).agentAlias(this.agentAlias).build());
                }
            }
        }, this.chatHandler::onError, () -> {
            this.chatHandler.onComplete(ChatResponse.builder().agentAlias(this.agentAlias).build());
        });
    }


}
