package com.gklx.mopga.admin.ai.domain;

import com.gklx.mopga.admin.ai.core.ChatHandler;
import lombok.Data;


@Data
public class AgentContext {

    private String conversationId;

    private String query;

    private String agentAlias;

    private String userId;

    private ChatHandler chatHandler;


}
