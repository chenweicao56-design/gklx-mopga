package com.gklx.mopga.admin.ai.memory;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;

import java.util.*;

public class CusChatMemoryStore implements ChatMemoryStore {

    Map<String, List<ChatMessage>> memorys = new HashMap<>();

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        List<ChatMessage> chatMessages = memorys.get(memoryId.toString());
        if (chatMessages == null) {
            return new ArrayList<>();
        }
        return chatMessages;


    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        List<ChatMessage> memory = memorys.get(memoryId.toString());
        if (Objects.isNull(memory)) {
            memory = new ArrayList<>(messages);
            memorys.put(memoryId.toString(), memory);
        } else {
            memory.clear();
            memory.addAll(messages);
        }

    }

    @Override
    public void deleteMessages(Object memoryId) {
        memorys.remove(memoryId.toString());
    }
}
