package com.gklx.mopga.admin.util;

import io.agentscope.core.agent.EventType;
import io.agentscope.core.agent.StreamOptions;

public class AgentUtil {


    public static StreamOptions getCommonStreamOptions() {
        return StreamOptions.builder()
                .eventTypes(EventType.REASONING, EventType.TOOL_RESULT)
                .incremental(true)
                .includeReasoningResult(false)
                .build();
    }
}
