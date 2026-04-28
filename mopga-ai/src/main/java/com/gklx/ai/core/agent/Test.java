package com.gklx.ai.core.agent;

import io.agentscope.core.message.Msg;
import io.agentscope.core.message.MsgRole;
import io.agentscope.core.message.TextBlock;
import io.agentscope.core.model.ChatResponse;
import io.agentscope.core.model.OpenAIChatModel;

import java.util.List;

public class Test {

    public static void main(String[] args) {
        OpenAIChatModel model = OpenAIChatModel.builder()
                .apiKey("sk-87c6cc6fb84440aca57f17305a6d69de")
                .modelName("deepseek-chat")
                .baseUrl("https://api.deepseek.com")
                .build();
        Msg usr = Msg.builder().role(MsgRole.USER).content(TextBlock.builder().text("你是谁").build()).build();
        Msg system = Msg.builder().role(MsgRole.SYSTEM).content(TextBlock.builder().text("你是谁").build()).build();

        List<ChatResponse> block = model.stream(List.of(system, usr), null, null).collectList().block();
        System.out.println(block);

    }
}
