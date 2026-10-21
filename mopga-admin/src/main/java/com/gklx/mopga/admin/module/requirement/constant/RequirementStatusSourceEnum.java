package com.gklx.mopga.admin.module.requirement.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 需求状态流转来源
 *
 * @author gklx
 */
@AllArgsConstructor
@Getter
public enum RequirementStatusSourceEnum {

    /**
     * 页面操作
     */
    PAGE(1, "页面操作"),

    /**
     * MCP调用
     */
    MCP(2, "MCP调用");

    private final Integer value;

    private final String desc;
}
