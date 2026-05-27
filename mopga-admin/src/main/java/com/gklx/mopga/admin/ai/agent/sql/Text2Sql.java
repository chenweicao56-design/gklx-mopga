package com.gklx.mopga.admin.ai.agent.sql;

import cn.hutool.core.map.MapUtil;
import com.gklx.ai.util.FreemarkerUtil;
import com.gklx.mopga.admin.ai.core.DbRule;
import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import com.gklx.mopga.admin.module.generate.domain.entity.DatabaseEntity;
import com.gklx.mopga.admin.module.generate.jdbc.JdbcSpiLoader;
import com.gklx.mopga.admin.module.generate.manager.DatabaseManager;
import com.gklx.mopga.admin.util.AgentUtil;
import io.agentscope.core.ReActAgent;
import io.agentscope.core.agent.Event;
import io.agentscope.core.hook.Hook;
import io.agentscope.core.hook.HookEvent;
import io.agentscope.core.hook.PostActingEvent;
import io.agentscope.core.memory.InMemoryMemory;
import io.agentscope.core.model.OpenAIChatModel;
import io.agentscope.core.tool.Toolkit;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service("text2SqlAgent")
public class Text2Sql extends BaseAgentService {

    @Resource
    private OpenAIChatModel openAIChatModel;
    @Resource
    private DatabaseManager databaseManager;

    @Override
    public void run(AgentContext agentContext) {
        try {
            Long databaseId = agentContext.getData().getLong("databaseId");
            DatabaseEntity database = databaseManager.getById(databaseId);
            String schema = agentContext.getData().getStr("schema");
            DbRule dbRule = JdbcSpiLoader.RuleDefines.get(database.getDatabaseType());

            Toolkit toolkit = new Toolkit();
            Map<String, Object> systemParams = MapUtil.<String, Object>builder()
                    .put("engine", database.getDatabaseType())
                    .put("schema", schema)
                    .put("quotRule", dbRule.getQuotRule())
                    .put("limitRule", dbRule.getLimitRule())
                    .put("otherRule", dbRule.getOtherRule())
                    .put("basicExample", dbRule.getBasicExample())
                    .put("exampleAnswer1", dbRule.getExampleAnswerListWithLimit().get(0))
                    .put("exampleAnswer2", dbRule.getExampleAnswerListWithLimit().get(0))
                    .put("exampleAnswer3", dbRule.getExampleAnswerListWithLimit().get(0))
                    .build();
            String systemPrompt = FreemarkerUtil.render("sql/system.md", systemParams);
            Hook planVisualizationHook =
                    new Hook() {
                        @Override
                        public <T extends HookEvent> Mono<T> onEvent(T event) {
                            if (event instanceof PostActingEvent postActing) {
                                String toolName = postActing.getToolUse().getName();
                            }
                            return Mono.just(event);
                        }
                    };
            ReActAgent agent =
                    ReActAgent.builder()
                            .name("text2SqlAgent")
                            .sysPrompt(systemPrompt)
                            .model(openAIChatModel)
                            .memory(new InMemoryMemory())
                            .toolkit(toolkit)
                            .maxIters(100)
                            .hooks(List.of(planVisualizationHook))
                            .build();
            Flux<Event> stream = agent.stream(agentContext.defaultUserMsg(), AgentUtil.getCommonStreamOptions());
            agentContext.subscribe(stream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
