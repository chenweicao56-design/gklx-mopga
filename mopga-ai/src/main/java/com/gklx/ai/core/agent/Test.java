package com.gklx.ai.core.agent;

import io.agentscope.core.ReActAgent;
import io.agentscope.core.embedding.EmbeddingModel;
import io.agentscope.core.memory.InMemoryMemory;
import io.agentscope.core.memory.Memory;
import io.agentscope.core.message.Msg;
import io.agentscope.core.message.MsgRole;
import io.agentscope.core.message.TextBlock;
import io.agentscope.core.model.ChatResponse;
import io.agentscope.core.model.OllamaChatModel;
import io.agentscope.core.model.OpenAIChatModel;
import io.agentscope.core.model.transport.HttpTransportConfig;
import io.agentscope.core.model.transport.HttpVersion;
import io.agentscope.core.model.transport.JdkHttpTransport;
import io.agentscope.core.rag.knowledge.SimpleKnowledge;
import io.agentscope.core.session.InMemorySession;
import io.agentscope.core.session.JsonSession;
import io.agentscope.core.session.Session;
import io.agentscope.core.session.SessionManager;
import io.agentscope.core.tool.Tool;
import io.agentscope.core.tool.ToolParam;
import io.agentscope.core.tool.Toolkit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IOException {

        Toolkit toolkit = new Toolkit();
        toolkit.registerTool(new SimpleTools());

        OpenAIChatModel model = OpenAIChatModel.builder()
                .apiKey("sk-87c6cc6fb84440aca57f17305a6d69de")
                .modelName("deepseek-chat")
                .baseUrl("https://api.deepseek.com")
                .build();

//        OpenAIChatModel model = OpenAIChatModel.builder()
//                .apiKey("sk-d29ca62a50634653982c90e410670c5e")
//                .modelName("qwen2.5-instruct")
//                .baseUrl("http://10.169.100.49:9991")
//                .httpTransport(JdkHttpTransport.builder().config(HttpTransportConfig.builder().httpVersion(HttpVersion.HTTP_1_1).build()).build())
//                .build();


//        Msg usr = Msg.builder().role(MsgRole.USER).content(TextBlock.builder().text("当前时间").build()).build();
//        Msg system = Msg.builder().role(MsgRole.SYSTEM).content(TextBlock.builder().text("你是AI机器人").build()).build();
//
//        List<ChatResponse> block = model.stream(List.of(system, usr), null, null).collectList().block();
//        System.out.println(block);
//        CusAgent cusAgent = CusAgent.builder().name("cusAgent").model(model).toolkit(toolkit).build();
//        Msg block1 = cusAgent.call(Arrays.asList(system, usr)).block();
//        System.out.println(block1);
        InMemorySession session = new InMemorySession();

     // 获取所有会话 Key

        CusAgent jarvis = CusAgent.builder()
                .name("Jarvis")
                .sysPrompt("你是一个名为 Jarvis 的助手")
                .model(model)
                .memory(new InMemoryMemory())
                .toolkit(toolkit)
                .build();



        // 发送消息
        Msg msg = Msg.builder()
                .textContent("你好！Jarvis，现在几点了？")
                .build();
        jarvis.saveTo(session, "user456");
        jarvis.saveTo(session, "user123");

        jarvis.loadIfExists(session, "user123");
        Msg response = jarvis.call(msg).block();
        System.out.println(response.getTextContent());
        jarvis.saveTo(session, "user123");
        Memory memory = jarvis.getMemory();
        jarvis.loadIfExists(session, "user456");
        session.listSessionKeys();
        Memory memory2 = jarvis.getMemory();
        jarvis.setSysPrompt("你是一个名为 gklx 的助手");
         msg = Msg.builder()
                .textContent("你叫啥？")
                .build();

         response = jarvis.call(msg).block();
        System.out.println(response.getTextContent());















//        InMemoryMemory memory = new InMemoryMemory();
//        ReActAgent agent = ReActAgent.builder()
//                .name("Assistant")
//                .model(model)
//                .memory(memory)
//                .build();
//
//// 创建 SessionManager，注册需要持久化的组件
//        Path sessionPath =
//                Paths.get("C:\\Users\\gklx", ".agentscope", "examples", "sessions");
//        if (!Files.exists(sessionPath)) {
//            Files.createDirectories(sessionPath);
//        }
//        String sessionId = "gklx";
//        Session session = new JsonSession(sessionPath);
//        SessionManager sessionManager = SessionManager.forSessionId(sessionId)
//                .withSession(session)
//                .addComponent(agent)
//                .addComponent(memory);
//
//
//        sessionManager.loadIfExists();
//
//        sessionManager.saveSession();
    }


}
