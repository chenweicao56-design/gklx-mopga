package com.gklx.mopga.admin.ai.agent.generate;

import com.gklx.mopga.admin.ai.domain.vo.ManagerAgentVo;
import dev.langchain4j.service.*;

public interface SqlGenerateAgent {

    @SystemMessage(fromResource = "prompt/generate/sql-create-system.txt")
    @UserMessage(fromResource = "prompt/generate/sql-create-user.txt")
    TokenStream run(@V("query") String query,@V("data") String data);
}
