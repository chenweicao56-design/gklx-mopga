package com.gklx.mopga.admin;


import cn.hutool.core.io.FileUtil;
import com.aliyun.core.utils.Base64Util;
import com.gklx.ai.util.MimeTypeUtil;
import com.gklx.mopga.admin.ai.agent.hook.FullObservabilityMiddleware;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import io.agentscope.core.ReActAgent;
import io.agentscope.core.event.AgentEventType;
import io.agentscope.core.event.TextBlockDeltaEvent;
import io.agentscope.core.message.Base64Source;
import io.agentscope.core.message.ImageBlock;
import io.agentscope.core.message.UserMessage;
import io.agentscope.core.model.DashScopeChatModel;
import io.agentscope.core.model.OpenAIChatModel;
import io.agentscope.core.session.Session;
import io.agentscope.core.skill.SkillFilter;
import io.agentscope.core.skill.repository.mysql.MysqlSkillRepository;
import io.agentscope.core.state.SimpleSessionKey;
import jakarta.annotation.Resource;
import okhttp3.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;

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
                .model(DashScopeChatModel.builder()
                        .apiKey("sk-bc047ae27a7c4b478f049886da1c15f0")
                        .modelName("qwen3.6-plus")
                        .build())
                .session(redisSession)
                .middleware(new FullObservabilityMiddleware())
                .sessionKey(SimpleSessionKey.of("11123"))
                .skillRepository(mysqlSkillRepository)
                .skillFilter(skillFilter)
                .build()) {


            //C:\Users\gklx\Desktop\11111.png
            File file = new File("C:\\Users\\gklx\\Desktop\\11111.png");
            byte[] fileContent = Files.readAllBytes(file.toPath());
            String base64String = Base64.getEncoder().encodeToString(fileContent);

            Base64Source source = Base64Source.builder().data(base64String).mediaType(MimeTypeUtil.getMimeType(file.getName())).build();
            agent.streamEvents(
                            List.of(UserMessage.builder()
                                    .content(ImageBlock.builder().source(source).build())
                                    .build(), new UserMessage("分析这张设计图，从布局、功能模块、交互逻辑、视觉规范四个维度输出文档，用表格和Markdown格式")))
                    .doOnNext(event -> {
                        if (event.getType() == AgentEventType.TEXT_BLOCK_DELTA) {
                            System.out.print(((TextBlockDeltaEvent) event).getDelta());
                        }
                    })
                    .blockLast();
        }
    }
}
