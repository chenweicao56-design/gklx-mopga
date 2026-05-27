package com.gklx.mopga.admin.ai.agent.plan;

import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import com.gklx.mopga.admin.util.AgentUtil;
import io.agentscope.core.ReActAgent;
import io.agentscope.core.agent.Event;
import io.agentscope.core.hook.Hook;
import io.agentscope.core.hook.HookEvent;
import io.agentscope.core.hook.PostActingEvent;
import io.agentscope.core.memory.InMemoryMemory;
import io.agentscope.core.model.OpenAIChatModel;
import io.agentscope.core.plan.PlanNotebook;
import io.agentscope.core.tool.Toolkit;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service("planAgent")
public class PlanAgent extends BaseAgentService {

    @Resource
    private OpenAIChatModel openAIChatModel;

    @Override
    public void run(AgentContext agentContext) {
        Toolkit toolkit = new Toolkit();
        PlanNotebook planNotebook = PlanNotebook.builder().build();
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
                        .name("PlanAgent")
                        .sysPrompt(
                                "You are a systematic assistant. For multi-step tasks:\n"
                                        + "1. Create a plan with create_plan tool\n"
                                        + "2. Execute subtasks one by one\n"
                                        + "3. Use finish_subtask after completing each\n"
                                        + "4. Call finish_plan when all done")
                        .model(openAIChatModel)
                        .memory(new InMemoryMemory())
                        .toolkit(toolkit)
                        .maxIters(100)
                        .hooks(List.of(planVisualizationHook))
                        .planNotebook(planNotebook)
                        .build();

        Flux<Event> stream = agent.stream(agentContext.defaultUserMsg(), AgentUtil.getCommonStreamOptions());
        agentContext.subscribe(stream);
    }
}
