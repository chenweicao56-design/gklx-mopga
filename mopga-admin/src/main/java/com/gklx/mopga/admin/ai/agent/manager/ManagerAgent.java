package com.gklx.mopga.admin.ai.agent.manager;

import com.gklx.mopga.admin.ai.domain.vo.ManagerAgentVo;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ManagerAgent {

    @SystemMessage(fromResource = "prompt/manager/system.txt")
    @UserMessage(fromResource = "prompt/manager/user.txt")
    ManagerAgentVo run(@V("query") String query, @V("engineers") String engineers);
}
