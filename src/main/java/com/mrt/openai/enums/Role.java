package com.mrt.openai.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Mr.T
 * @Date: 2024/5/11
 */

@Getter
@AllArgsConstructor
public enum Role {
    /**
     *  系统
     */
    SYSTEM("system"),
    /**
     * 用户
     */
    USER("user"),
    /**
     * 助手
     */
    ASSISTANT("assistant"),
    /**
     * 好像已经 不支持
     */
    @Deprecated
    FUNCTION("function"),
    /**
     * 工具
     */
    TOOL("tool"),
    ;
    private final String name;
}
