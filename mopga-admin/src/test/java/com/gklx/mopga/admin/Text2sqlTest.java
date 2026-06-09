package com.gklx.mopga.admin;


import com.gklx.mopga.admin.ai.agent.hook.FullObservabilityMiddleware;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import io.agentscope.core.ReActAgent;
import io.agentscope.core.event.AgentEventType;
import io.agentscope.core.event.TextBlockDeltaEvent;
import io.agentscope.core.message.UserMessage;
import io.agentscope.core.model.OpenAIChatModel;
import io.agentscope.core.session.InMemorySession;
import io.agentscope.core.session.Session;
import io.agentscope.core.session.redis.RedisSession;
import io.agentscope.core.skill.SkillFilter;
import io.agentscope.core.skill.repository.mysql.MysqlSkillRepository;
import io.agentscope.core.state.SimpleSessionKey;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class Text2sqlTest {

    @Resource(name = "CodeGenerateAgent")
    private BaseAgentService codeGenerateAgent;

    @Resource
    private OpenAIChatModel openAIChatModel;

    @Resource
    Session redisSession;
    @Resource
    MysqlSkillRepository mysqlSkillRepository;



    @Test
    void contextLoads() throws Exception {
        SkillFilter skillFilter = SkillFilter.all();
        try (ReActAgent agent = ReActAgent.builder()
                .sysPrompt("你叫tom")
                .model(openAIChatModel)
                .session(redisSession)
                .middleware(new FullObservabilityMiddleware())
                .sessionKey(SimpleSessionKey.of("11123"))
                .skillRepository(mysqlSkillRepository)
                .skillFilter(skillFilter)
                .build()) {
            agent.streamEvents(new UserMessage("当前时间"))
                    .doOnNext(event -> {
                        if (event.getType() == AgentEventType.TEXT_BLOCK_DELTA) {
                            System.out.print(((TextBlockDeltaEvent) event).getDelta());
                        }
                    })
                    .blockLast();
        }
    }
}
