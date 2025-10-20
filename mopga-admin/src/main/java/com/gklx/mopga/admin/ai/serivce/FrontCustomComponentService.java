package com.gklx.mopga.admin.ai.serivce;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import reactor.core.publisher.Flux;

public interface FrontCustomComponentService {

    @SystemMessage(fromResource = "prompt/system/front-custom-component.txt")
    @UserMessage(fromResource = "prompt/user/front-custom-component.txt")
    Flux<String> chat(@V("requirement") String requirement);

}
