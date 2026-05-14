package com.gklx.mopga.admin.ai.agent.router;

import cn.hutool.json.JSONUtil;
import com.gklx.mopga.admin.ai.agent.manager.ManagerAgent;
import com.gklx.mopga.admin.ai.core.ChatHandler;
import com.gklx.mopga.admin.ai.core.ChatResponse;
import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import com.gklx.mopga.admin.module.system.menu.domain.vo.MenuVO;
import com.gklx.mopga.base.module.support.redis.RedisService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("routerAgentService")
public class RouterAgentService extends BaseAgentService {

    public static final String AGENT_ALIAS = "routerAgent";

    @Resource
    private RouterAgent routerAgent;
    @Resource
    private RedisService redisService;

    @Override
    public void run(AgentContext agentContext) {
        ChatHandler chatHandler = agentContext.getChatHandler();
        String cache = redisService.get("user:menu:" + agentContext.getUserId());
        List<MenuVO> menus = JSONUtil.toList(cache, MenuVO.class);
        RouterResponse run = routerAgent.run(agentContext.getQuery(), formatMenus(menus));
        if (!"0".equals(run.menuId())) {
            MenuVO menuVO = menus.stream().filter(menu -> menu.getMenuId().toString().equals(run.menuId())).findFirst().orElse(null);
            chatHandler.onAnswer(ChatResponse.builder().content(String.format("您要找的菜单是：%s", menuVO.getMenuName())).build());
            chatHandler.onComplete(ChatResponse.builder().content("现在给你跳转").menu(JSONUtil.toJsonStr(menuVO)).agentAlias(ManagerAgent.AGENT_ALIAS).complete(true).build());
        } else {
            chatHandler.onComplete(ChatResponse.builder().content("没有找到菜单").agentAlias(AGENT_ALIAS).complete(true).build());

        }
    }

    private String formatMenus(List<MenuVO> menus) {
        return JSONUtil.toJsonStr(menus);
    }


}
