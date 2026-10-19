package com.gklx.mopga.admin.ai.domain;

import cn.hutool.json.JSONObject;
import com.gklx.mopga.admin.ai.core.ChatHandler;
import com.gklx.mopga.admin.ai.core.ChatResponse;
import lombok.Data;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 智能体上下文，承载一次会话的输入与流式输出。
 */
@Data
public class AgentContext {

    private String conversationId;

    private String query;

    private String agentAlias;

    private String userId;

    private ChatHandler chatHandler;

    private JSONObject data;

    private List<String> files;

    /**
     * 订阅文本增量流式响应，逐块回调聊天处理器。
     *
     * @param stream 文本增量流
     */
    public void subscribe(Flux<String> stream) {
        // 过滤空增量，避免向前端推送无意义的空包
        stream.subscribe(
                delta -> {
                    if (delta != null && !delta.isEmpty()) {
                        this.chatHandler.onAnswer(ChatResponse.builder()
                                .content(delta)
                                .agentAlias(this.agentAlias)
                                .build());
                    }
                },
                this.chatHandler::onError,
                () -> this.chatHandler.onComplete(ChatResponse.builder()
                        .agentAlias(this.agentAlias)
                        .build()));
    }
}
