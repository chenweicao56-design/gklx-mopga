package com.gklx.ai.core.agent;

import io.agentscope.core.agent.AgentBase;
import io.agentscope.core.agent.accumulator.ReasoningContext;
import io.agentscope.core.interruption.InterruptContext;
import io.agentscope.core.message.Msg;
import io.agentscope.core.model.Model;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class CusAgent extends AgentBase {

    private final Model model;

    public CusAgent(CusAgent.Builder builder) {
        super(builder.name, builder.description);
        this.model = builder.model;
    }

    @Override
    protected Mono<Msg> doCall(List<Msg> msgs) {
        ReasoningContext context = new ReasoningContext(getName());

        return checkInterruptedAsync()
                .thenMany(model.stream(msgs, null, null)
                        .concatMap(chunk -> checkInterruptedAsync().thenReturn(chunk))
                        .doOnNext(context::processChunk)
                )
                .then(Mono.fromCallable(context::buildFinalMessage))
                .onErrorResume(InterruptedException.class, error -> {
                    Msg partialMsg = context.buildFinalMessage();
                    if (partialMsg != null) {
                        log.warn("Reasoning interrupted but partial result available: {}", partialMsg);
                    }
                    return Mono.error(error);
                })
                .doOnNext( msg -> log.info("Final message: {}", msg));
    }

    @Override
    protected Mono<Msg> handleInterrupt(InterruptContext context, Msg... originalArgs) {
        return handleInterrupt(context, originalArgs);
    }

    public static CusAgent.Builder builder() {
        return new CusAgent.Builder();
    }

    public static class Builder {

        private Model model;
        private String name;
        private String description;

        private Builder() {
        }

        public CusAgent.Builder model(Model model) {
            this.model = model;
            return this;
        }

        public CusAgent.Builder name(String name) {
            this.name = name;
            return this;
        }

        public CusAgent.Builder description(String description) {
            this.description = description;
            return this;
        }
    }
}
