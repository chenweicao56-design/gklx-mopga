package com.gklx.mopga.admin.ai.agent.manager;

import cn.hutool.json.JSONUtil;
import com.gklx.mopga.admin.ai.core.ChatHandler;
import com.gklx.mopga.admin.ai.core.ChatResponse;
import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.domain.entity.AgentEntity;
import com.gklx.mopga.admin.ai.domain.vo.ManagerAgentVo;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("managerAgentService")
public class ManagerAgentService extends BaseAgentService {

    public static final String AGENT_ALIAS = "managerAgent";

    @Resource
    private ManagerAgent managerAgent;

    @Override
    public void run(AgentContext agentContext) {
        ChatHandler handler = agentContext.getChatHandler();
        List<AgentEntity> agentList = getAgentList();
        ManagerAgentVo run = managerAgent.run(agentContext.getConversationId()+":manager",agentContext.getQuery(), formatAgents(agentList));
        if (!"0".equals(run.id())) {
            agentContext.setAgentAlias(run.alias());
            handler.onTransfer(agentContext);
        } else {
            List<String> list = agentList.stream().map(AgentEntity::getName).toList();
            handler.onAnswer(ChatResponse.builder().content("很抱歉，我暂时无法回答你的问题\n").build());
            handler.onAnswer(ChatResponse.builder().content("我暂时只能提供以下服务：\n").build());
            handler.onComplete(ChatResponse.builder().content(String.join("\n", list)).build());
        }
    }

    private String formatAgents(List<AgentEntity> agentList) {
        return JSONUtil.toJsonStr(agentList);
    }

    public static List<AgentEntity> getAgentList() {
        List<AgentEntity> list = new ArrayList<>();
        AgentEntity userAgent = new AgentEntity();
        userAgent.setId("1");
        userAgent.setName("用户模块工程师");
        userAgent.setAlias("userAgent");
        userAgent.setDescription("负责管理用户模块，包含查询、新增、修改、删除用户等功能");
        list.add(userAgent);

        AgentEntity deptAgent = new AgentEntity();
        deptAgent.setId("2");
        deptAgent.setName("部门模块工程师");
        deptAgent.setAlias("deptAgent");
        deptAgent.setDescription("负责管理部门模块，包含查询、新增、修改、删除部门等功能");
        list.add(deptAgent);

        AgentEntity dictAgent = new AgentEntity();
        dictAgent.setId("3");
        dictAgent.setName("字典模块工程师");
        dictAgent.setAlias("dictAgent");
        dictAgent.setDescription("负责管理字典模块，包含查询、新增、修改、删除字典等功能");
        list.add(dictAgent);

        AgentEntity menuAgent = new AgentEntity();
        menuAgent.setId("4");
        menuAgent.setName("菜单模块工程师");
        menuAgent.setAlias("menuAgent");
        menuAgent.setDescription("负责管理菜单模块，包含查询、新增、修改、删除菜单等功能");
        list.add(menuAgent);

        AgentEntity routerAgent = new AgentEntity();
        routerAgent.setId("5");
        routerAgent.setName("路由模块工程师");
        routerAgent.setAlias("routerAgent");
        routerAgent.setDescription("负责管理整个系统的路由");
        routerAgent.setKeywords(List.of("路由", "跳转"));
        list.add(routerAgent);


        AgentEntity docAgent = new AgentEntity();
        docAgent.setId("6");
        docAgent.setName("文档模块工程师");
        docAgent.setAlias("docAgent");
        docAgent.setDescription("负责管理整个系统的文档");
        docAgent.setKeywords(List.of("规范", "文档"));
        list.add(docAgent);

        return list;
    }
}
