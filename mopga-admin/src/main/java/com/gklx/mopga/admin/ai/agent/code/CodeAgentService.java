package com.gklx.mopga.admin.ai.agent.code;

import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import io.agentscope.core.ReActAgent;
import io.agentscope.core.model.OpenAIChatModel;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("codeAgentService")
public class CodeAgentService extends BaseAgentService {

    @Resource
    private OpenAIChatModel openAIChatModel;

    @Override
    public void run(AgentContext agentContext) {
    }
}
