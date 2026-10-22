package com.gklx.mopga.admin.module.knowledge.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 知识库-文档状态枚举
 *
 * @author gklx
 */
@AllArgsConstructor
@Getter
public enum KbDocumentStatusEnum {

    /**
     * 上传中转
     */
    UPLOADING(0, "上传中转"),

    /**
     * 待解析
     */
    PENDING_PARSE(1, "待解析"),

    /**
     * 解析中
     */
    PARSING(2, "解析中"),

    /**
     * 已解析
     */
    PARSED(3, "已解析"),

    /**
     * 分段中
     */
    SEGMENTING(4, "分段中"),

    /**
     * 向量化中
     */
    VECTORIZING(5, "向量化中"),

    /**
     * 已生效
     */
    EFFECTIVE(6, "已生效"),

    /**
     * 解析失败
     */
    PARSE_FAILED(7, "解析失败"),

    /**
     * 向量化失败
     */
    VECTOR_FAILED(8, "向量化失败");

    private final Integer value;

    private final String desc;

    public static KbDocumentStatusEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (KbDocumentStatusEnum item : KbDocumentStatusEnum.values()) {
            if (item.value.equals(value)) {
                return item;
            }
        }
        return null;
    }
}
