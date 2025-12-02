package com.gklx.mopga.admin.ai.agent.dict;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.model.output.structured.Description;

public class DictTools {


    @Description("模块唯一标识")
    public record Dict(
            @Description("字典名字") String dictName,
            @Description("字典编码") String dictCode,
            @Description("字典备注") @JsonProperty(required = false) String remark
    ) {
    }

    @Tool(name = "addDict", value = """
            该工具用于在系统中新增字典。
            """
    )
    public String add(Dict dict) {

        return "新增字典成功";
    }
    @Tool(name = "updateDict", value = """
            该工具用于在系统中更新字典。
            """
    )
    public String update(Dict dict) {

        return "更新字典成功";
    }

}
