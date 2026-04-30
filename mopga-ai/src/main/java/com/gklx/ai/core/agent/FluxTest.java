package com.gklx.ai.core.agent;

import reactor.core.publisher.Mono;

public class FluxTest {

    public static void main(String[] args) throws InterruptedException {
        // 模拟数据库查询，每次订阅都重新查询
        Mono<String> getUserMono = Mono.defer(() ->
                Mono.fromCallable(() -> "查询结果")
        );

// 每次订阅都会触发新的数据库查询
        getUserMono.subscribe(System.out::println); // 查询1
        Thread.sleep(1000);
        getUserMono.subscribe(System.out::println); // 查询2（最新的数据）
    }
}
