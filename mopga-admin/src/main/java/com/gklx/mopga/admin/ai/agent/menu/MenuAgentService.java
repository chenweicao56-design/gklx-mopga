package com.gklx.mopga.admin.ai.agent.menu;

import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("menuAgentService")
public class MenuAgentService extends BaseAgentService {

    public static final String AGENT_ALIAS = "menuAgent";

    @Resource
    private menuAgent menuAgent;


    @Override
    public void run(AgentContext agentContext) {
        String chat = menuAgent.chat(agentContext.getQuery());
        System.out.println(chat);

    }
}
