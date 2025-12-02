package com.gklx.mopga.admin.ai.agent.router;

import dev.langchain4j.model.output.structured.Description;

@Description("菜单")
public record RouterResponse(
        @Description("菜单唯一标识符") String menuId,
        @Description("菜单名称") String menuName) {
}
