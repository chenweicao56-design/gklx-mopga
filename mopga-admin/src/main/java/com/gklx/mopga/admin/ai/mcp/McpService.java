package com.gklx.mopga.admin.ai.mcp;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.stereotype.Service;

@Service
public class McpService {

    @McpTool(description = "Get the temperature (in celsius) for a specific location")
    public String getWeatherByCity(String city) {
        if ("北京".equals(city)) {
            return "北京今天是晴天，温度25°C，空气质量良好。";
        } else if ("上海".equals(city)) {
            return "上海今天是多云，温度22°C，微风。";
        }
        return String.format("暂时无法获取城市 '%s' 的天气信息。", city);
    }
}
