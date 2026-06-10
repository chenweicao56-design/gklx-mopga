package com.gklx.mopga.admin.ai.agent;


import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import io.agentscope.core.model.OpenAIChatModel;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("GenerateAgent")
public class GenerateAgent extends BaseAgentService {
    @Resource
    private OpenAIChatModel openAIChatModel;

    @Override
    public void run(AgentContext agentContext) {

    }
}
