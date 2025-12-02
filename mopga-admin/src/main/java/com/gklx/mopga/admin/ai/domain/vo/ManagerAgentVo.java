package com.gklx.mopga.admin.ai.domain.vo;

import dev.langchain4j.model.output.structured.Description;

@Description("模块")
public record ManagerAgentVo(
        @Description("模块唯一标识") String id,
        @Description("模块别名") String alias
) {
}