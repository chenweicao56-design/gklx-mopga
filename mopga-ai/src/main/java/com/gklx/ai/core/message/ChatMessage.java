package com.gklx.ai.core.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessage {

    @Builder.Default
    private String role = Constant.CHAT_ROLE_USER;

    @Builder.Default
    private String content = "";
}
