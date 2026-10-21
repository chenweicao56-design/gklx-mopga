package com.gklx.mopga.admin.module.requirement.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 需求生命周期状态
 *
 * @author gklx
 */
@AllArgsConstructor
@Getter
public enum RequirementStatusEnum {

    /**
     * 待开发
     */
    PENDING(1, "待开发"),

    /**
     * 开发中
     */
    DEVELOPING(2, "开发中"),

    /**
     * 测试中
     */
    TESTING(3, "测试中"),

    /**
     * 已完成
     */
    FINISHED(4, "已完成"),

    /**
     * 已交付
     */
    DELIVERED(5, "已交付"),

    /**
     * 失败
     */
    FAILED(6, "失败");

    private final Integer value;

    private final String desc;

    /**
     * 根据value获取枚举
     */
    public static RequirementStatusEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (RequirementStatusEnum item : RequirementStatusEnum.values()) {
            if (item.value.equals(value)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 是否为终态(已交付/失败不允许再流转)
     */
    public static boolean isTerminal(Integer value) {
        return DELIVERED.value.equals(value) || FAILED.value.equals(value);
    }
}
