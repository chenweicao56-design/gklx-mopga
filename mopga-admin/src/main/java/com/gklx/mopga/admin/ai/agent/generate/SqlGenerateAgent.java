package com.gklx.mopga.admin.ai.agent.generate;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface SqlGenerateAgent {

    @SystemMessage(fromResource = "prompt/generate/sql-create-system.txt")
    @UserMessage(fromResource = "prompt/generate/sql-create-user.txt")
    TokenStream run(@V("query") String query,
                    @V("dbEngine") String dbEngine,
                    @V("currentTableStructure") String currentTableStructure,
                    @V("commonColumnType") String commonColumnType,
                    @V("designDrawing") String designDrawing
    );
}
