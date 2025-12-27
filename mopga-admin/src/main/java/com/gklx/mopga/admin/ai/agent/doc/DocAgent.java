package com.gklx.mopga.admin.ai.agent.doc;

import dev.langchain4j.service.*;

/**
 * Doc Agent Interface
 */
public interface DocAgent {

    @SystemMessage(fromResource = "prompt/doc/system.txt")
    @UserMessage(fromResource = "prompt/user.txt")
    TokenStream run(@MemoryId String memoryId, @V("query") String query);

}
