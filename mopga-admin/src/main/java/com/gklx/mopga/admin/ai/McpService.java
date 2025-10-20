package com.gklx.mopga.admin.ai;

import reactor.core.publisher.Flux;

public interface McpService {
    Flux<String> chat(String message);
}
