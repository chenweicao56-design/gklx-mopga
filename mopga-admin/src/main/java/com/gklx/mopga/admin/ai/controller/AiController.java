package com.gklx.mopga.admin.ai.controller;

import com.gklx.mopga.admin.ai.McpService;
import com.gklx.mopga.admin.ai.agent.menu.MenuAgentService;
import com.gklx.mopga.admin.ai.core.ChatResponse;
import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.ChatService;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AiController {

    @Resource
    private OpenAiStreamingChatModel openAiStreamingChatModel;
    @Resource
    private ChatService chatService;
    @Resource
    private MenuAgentService menuAgentService;

    @PostMapping("/chat")
    public Flux<ChatResponse> chat(@RequestBody AgentContext context) {
        return chatService.chat(context);
    }

    @Resource
    private ContentRetriever menuContentRetriever;

    @GetMapping("/test")
    public Flux<String> test() {

        List<Content> department = menuContentRetriever.retrieve(Query.from("代码预览"));

//        AgentContext agentContext = new AgentContext();
//        agentContext.setConversationId("1234567890");
//        agentContext.setQuery("部门管理");
//        agentContext.setAgentAlias(MenuAgentService.AGENT_ALIAS);
//        menuAgentService.run(agentContext);
        return Flux.just("");


//        return Flux.create(sink -> {
//            // 使用原子类计数，避免线程安全问题
//            AtomicInteger counter = new AtomicInteger(1);
//
//            // 创建一个后台线程持续发送消息
//            Thread thread = new Thread(() -> {
//                while (!sink.isCancelled()) { // 检查订阅是否取消
//                    try {
//                        String message = "Hello " + counter.getAndIncrement();
//                        sink.next(message);
//                        Thread.sleep(1000); // 每秒发送一条
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                        sink.error(e);
//                        break;
//                    }
//                }
//            });
//            thread.start();
//
//            // 当订阅取消时停止线程
//            sink.onDispose(thread::interrupt);
//        });
    }

    @GetMapping("/mcp")
    public Flux<ServerSentEvent<String>> mcp(String message) {
        Map<String, String> env = new HashMap<>();
        env.put("MYSQL_HOST", "180.76.50.217");
        env.put("MYSQL_PORT", "3306");
        env.put("MYSQL_USER", "root");
        env.put("MYSQL_PASS", "Qaz@wsx");
        env.put("MYSQL_DB", "homekeep");
        env.put("ALLOW_INSERT_OPERATION", "false");
        env.put("ALLOW_UPDATE_OPERATION", "false");
        env.put("ALLOW_DELETE_OPERATION", "false");

        McpTransport transport = new StdioMcpTransport.Builder()
                .command(List.of("cmd", "/c", "npx", "-y", "@f4ww4z/mcp-mysql-server"))
                .environment(env)
                .build();
        McpClient mcpClient = new DefaultMcpClient.Builder()
                .transport(transport)
                .logHandler(System.out::println)
                .build();
        McpToolProvider toolProvider = McpToolProvider.builder()
                .mcpClients(mcpClient)
                .build();
        McpService mcpService = AiServices.builder(McpService.class)
                .streamingChatModel(openAiStreamingChatModel)
                .toolProvider(toolProvider)
                .build();
        return mcpService.chat(message).map(chunk -> ServerSentEvent.<String>builder()
                .data(chunk)
                .build());
    }
}
