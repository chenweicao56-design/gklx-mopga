package com.gklx.mopga.admin.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SolveStatus {

    // 已解决
    FINISH("finish"),
    // 需要转交
    TRANSFER("transfer"),
    //需要继续沟通
    COMMUNICATE("communicate");

    private final String value;


}