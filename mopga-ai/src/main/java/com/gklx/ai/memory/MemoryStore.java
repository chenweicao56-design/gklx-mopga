package com.gklx.ai.memory;

import com.gklx.ai.core.message.ChatMessage;

import java.util.List;

public interface MemoryStore {

    List<ChatMessage> getMessages(Object var1);

    void updateMessages(Object var1, List<ChatMessage> var2);

    void deleteMessages(Object var1);
}
