package com.gklx.mopga.admin.ai.agent.dict;

import dev.langchain4j.service.*;

/**
 * dictionary agent interface
 */
public interface DictAgent {

    @SystemMessage(fromResource = "prompt/dict/system.txt")
    @UserMessage(fromResource = "prompt/user.txt")
    TokenStream run(@MemoryId String memoryId, @V("query") String query);

    @SystemMessage(fromResource = "prompt/dict/system.txt")
    @UserMessage(fromResource = "prompt/dict/user.txt")
    String call(@MemoryId String memoryId, @V("query") String query);


}
