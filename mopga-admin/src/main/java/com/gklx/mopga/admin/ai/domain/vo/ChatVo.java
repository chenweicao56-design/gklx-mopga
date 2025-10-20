package com.gklx.mopga.admin.ai.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ChatVo {

    @Schema(description = "内容")
    private String content;

    @Schema(description = "类型")
    private String type;


}
