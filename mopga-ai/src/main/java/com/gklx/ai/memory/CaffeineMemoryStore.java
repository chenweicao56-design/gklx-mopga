package com.gklx.ai.memory;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.gklx.ai.core.message.ChatMessage;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CaffeineMemoryStore implements MemoryStore{



    Cache<Object, Object> cache = Caffeine.newBuilder()
            //初始数量
            .initialCapacity(10)
            //最大条数
            .maximumSize(10)
            //expireAfterWrite和expireAfterAccess同时存在时，以expireAfterWrite为准
            //最后一次写操作后经过指定时间过期
            .expireAfterWrite(1, TimeUnit.SECONDS)
            //最后一次读或写操作后经过指定时间过期
            .expireAfterAccess(1, TimeUnit.SECONDS)
            //监听缓存被移除
            .removalListener((key, val, removalCause) -> { })
            //记录命中
            .recordStats()
            .build();


    @Override
    public List<ChatMessage> getMessages(Object var1) {
        return List.of();
    }

    @Override
    public void updateMessages(Object var1, List<ChatMessage> var2) {

    }

    @Override
    public void deleteMessages(Object var1) {

    }
}
