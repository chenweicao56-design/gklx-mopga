package com.gklx.mopga.admin.ai.domain;

import cn.hutool.json.JSONObject;
import com.gklx.mopga.admin.ai.core.ChatHandler;
import com.gklx.mopga.admin.ai.core.ChatResponse;
import io.agentscope.core.agent.Event;
import io.agentscope.core.event.AgentEvent;
import io.agentscope.core.event.AgentEventType;
import io.agentscope.core.event.TextBlockDeltaEvent;
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

    public void subscribe(Flux<AgentEvent> stream) {
        stream.subscribe(event -> {
            System.out.println(event.getType());

            if (event.getType() == AgentEventType.TEXT_BLOCK_DELTA) {
                this.chatHandler.onAnswer(ChatResponse.builder().content(((TextBlockDeltaEvent) event).getDelta()).agentAlias(this.agentAlias).build());

            }
        }, this.chatHandler::onError, () -> {
            this.chatHandler.onComplete(ChatResponse.builder().agentAlias(this.agentAlias).build());
        });
    }


}
