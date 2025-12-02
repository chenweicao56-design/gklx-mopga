package com.gklx.mopga.admin.ai.memory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryConfig {


    @Bean
    CusChatMemoryStore cusChatMemoryStore() {
        return new CusChatMemoryStore();
    }

}
