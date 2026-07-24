package com.gklx.ai.core.agent;

import io.agentscope.core.message.Msg;
import io.agentscope.core.model.Model;
import reactor.core.publisher.Mono;

import java.util.List;

public class ReActAgent extends BaseAgent {

    private Model model;

    private int maxIters;

    protected Mono<Msg> doCall(List<Msg> msgs) {

        return null;
    }
}
