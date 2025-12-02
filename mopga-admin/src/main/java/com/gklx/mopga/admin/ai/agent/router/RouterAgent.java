package com.gklx.mopga.admin.ai.agent.router;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface RouterAgent {

    @SystemMessage(fromResource = "prompt/router/system.txt")
    @UserMessage(fromResource = "prompt/user.txt")
    RouterResponse run(@V("query") String query, @V("menus") String menus);
}
