package com.gklx.mopga.admin.ai.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class SimpleTools {

    @Tool(description = "Returns the current time in a given IANA timezone.")
    public String getCurrentTime(
            @ToolParam(description = "IANA timezone, e.g. Asia/Shanghai")
            String timezone) {
        return LocalDateTime.now(ZoneId.of(timezone))
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
