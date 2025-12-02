package com.gklx.mopga.admin.ai.mcp;//package com.gklx.ai.mcp;
//
//import dev.langchain4j.mcp.McpToolProvider;
//import dev.langchain4j.mcp.client.DefaultMcpClient;
//import dev.langchain4j.mcp.client.McpClient;
//import dev.langchain4j.mcp.client.transport.McpTransport;
//import dev.langchain4j.mcp.client.transport.http.StreamableHttpMcpTransport;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class McpConfig {
//
//    @Value("${bigmodel.api-key}")
//    private String apiKey;
//
//    @Bean
//    public McpToolProvider mcpToolProvider() {
//        // 和 MCP 服务通讯
////        McpTransport transport = new McpHttpTransport.Builder()
////                .sseUrl("https://open.bigmodel.cn/api/mcp/web_search/sse?Authorization=" + apiKey)
////                .logRequests(true) // 开启日志，查看更多信息
////                .logResponses(true)
////                .build();
//        McpTransport transport = new StreamableHttpMcpTransport.Builder()
//                .url("http://localhost:3001/mcp")
//                .logRequests(true) // if you want to see the traffic in the log
//                .logResponses(true)
//                .build();
//        // 创建 MCP 客户端
//        McpClient mcpClient = new DefaultMcpClient.Builder()
//                .key("yupiMcpClient")
//                .transport(transport)
//                .build();
//        // 从 MCP 客户端获取工具
//        McpToolProvider toolProvider = McpToolProvider.builder()
//                .mcpClients(mcpClient)
//                .build();
//        return toolProvider;
//    }
//}
