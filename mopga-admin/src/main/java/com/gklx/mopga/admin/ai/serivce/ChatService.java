package com.gklx.mopga.admin.ai.serivce;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.gklx.mopga.admin.ai.agent.manager.ManagerAgentService;
import com.gklx.mopga.admin.ai.core.ChatHandler;
import com.gklx.mopga.admin.ai.core.ChatResponse;
import com.gklx.mopga.admin.ai.domain.AgentContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author GKLX
 * @description: AI服务
 * @create 2021-02-25 16:00
 */
@Slf4j
@Service
public class ChatService extends BaseChatService {
    @Resource
    private ManagerAgentService managerAgentService;

    public Flux<ChatResponse> chat(AgentContext context) {

        return Flux.create(sink -> {
            ChatHandler chatHandler = new ChatHandler() {

                @Override
                public void onAnswer(ChatResponse message) {
                    sink.next(message);
                }

                @Override
                public void onComplete(ChatResponse message) {
                    message.setComplete(true);
                    sink.next(message);
                    sink.complete();
                }

                @Override
                public void onError(Throwable e) {
                    sink.next(ChatResponse.builder().complete(true).content("我可能遇到了一些问题，请稍后再试。").build());
                    sink.complete();
                }

                @Override
                public void onTransfer(AgentContext agentContext) {
                    BaseAgentService agentService = SpringUtil.getBean(
                            context.getAgentAlias() + "Service",
                            BaseAgentService.class
                    );
                    agentService.run(context);
                }
            };
            context.setChatHandler(chatHandler);
            String agentAlias = context.getAgentAlias();
            if (StrUtil.isNotEmpty(agentAlias)) {
                BaseAgentService agentService = SpringUtil.getBean(
                        agentAlias,
                        BaseAgentService.class
                );
                agentService.run(context);
            } else {
                managerAgentService.run(context);
            }
        });
    }
}
