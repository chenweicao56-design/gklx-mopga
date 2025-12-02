package com.gklx.mopga.admin.ai.domain.vo;

import dev.langchain4j.model.output.structured.Description;

@Description("a person")
public record TestVo(@Description("person's first and last name, for example: John Doe") String name) {
}
