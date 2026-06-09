package com.gklx.mopga.admin.ai.mcp;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

@Service
public class GenerateMcp {

    @McpTool(description = "根据表名同步数据")
    public String syncTable(@McpToolParam(description = "数据库主键") Long databaseId,
                            @McpToolParam(description = "需要同步的表名，多个用，隔开") String tableNames) {
        return "同步成功";
    }
}
