package com.gklx.ai.core.entity;

import java.util.List;

public class RequestParams {

    private List<Message> messages;

    private String model;

    private String thinking;

    private String frequencyPenalty;

    private Integer maxTokens;

    private String responseFormat;

    private RequestParams(Builder builder) {
        this.messages = builder.messages;
    }

    public static class Builder {

        private final List<Message> messages;

        public Builder(List<Message> messages) {
            this.messages = messages;
        }

        public RequestParams build() {
            return new RequestParams(this);
        }
    }
}
