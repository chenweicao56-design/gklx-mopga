package com.gklx.mopga.admin.ai.core;

import com.gklx.mopga.admin.constant.AiConst;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 *  chat response
 */
@Data
@Builder
public class ChatResponse {

    @Schema(description = "内容")
    @Builder.Default
    private String content = "";

    @Schema(description = "类型")
    @Builder.Default
    private String type = AiConst.ROLE_AI;

    @Schema(description = "流式最后一条")
    @Builder.Default
    private Boolean complete = false;

    @Schema(description = "智能体别名")
    private String agentAlias;

    @Schema(description = "解决状态")
    private String solveStatus;

    @Schema(description = "交接智能体别名")
    private String transferAgentAlias;

    @Schema(description = "路由菜单")
    private String menu;




}
