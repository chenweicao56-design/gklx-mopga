package com.gklx.mopga.admin.ai.core;

import lombok.Data;

import java.util.List;

@Data
public class DbRule {

    private String databaseType;
    private String quotRule;
    private String limitRule;
    private String otherRule;
    private String basicExample;
    private List<String> exampleAnswerList;
    private List<String> exampleAnswerListWithLimit;
}
