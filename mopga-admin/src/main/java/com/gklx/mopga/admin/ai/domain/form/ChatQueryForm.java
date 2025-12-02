package com.gklx.mopga.admin.ai.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ChatQueryForm {

    @Schema(description = "问题")
    private String question;

    @Schema(description = "会话ID")
    private String conversationId;

    @Schema(description = "智能体别名")
    private String agentAlias;


}
