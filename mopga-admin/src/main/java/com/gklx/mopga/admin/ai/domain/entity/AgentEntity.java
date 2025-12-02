package com.gklx.mopga.admin.ai.domain.entity;

import lombok.Data;

import java.util.List;

@Data
public class AgentEntity {

    private String id;

    private String alias;

    private String name;

    private String description;

    private List<String> questions;

    private List<String> keywords;
}
