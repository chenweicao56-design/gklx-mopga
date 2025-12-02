package com.gklx.mopga.admin.ai.core;

import com.gklx.mopga.admin.ai.domain.AgentContext;

public interface ChatHandler {

    void onAnswer(ChatResponse response);

    void onComplete(ChatResponse response);

    void onError(Throwable e);

    void onTransfer(AgentContext agentContext);

}
