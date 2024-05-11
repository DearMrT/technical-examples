package com.mrt.openai.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 最新模型参考官方文档：
 * <a href="https://platform.openai.com/docs/models/model-endpoint-compatibility">官方稳定模型列表</a>
 * @Author: Mr.T
 * @Date: 2024/5/11
 */
@Getter
@AllArgsConstructor
public enum Model {
    /**
     * gpt-3.5-turbo
     */
    GPT_3_5_TURBO("gpt-3.5-turbo"),
    /**
     * 临时模型，不建议使用，2023年9 月 13 日将被弃用
     */
    @Deprecated
    GPT_3_5_TURBO_0301("gpt-3.5-turbo-0301"),
    /**
     * gpt-3.5-turbo-0613 支持函数
     */
    GPT_3_5_TURBO_0613("gpt-3.5-turbo-0613"),
    /**
     * gpt-3.5-turbo-16k 超长上下文
     */
    GPT_3_5_TURBO_16K("gpt-3.5-turbo-16k"),
    /**
     * gpt-3.5-turbo-16k-0613 超长上下文 支持函数
     */
    GPT_3_5_TURBO_16K_0613("gpt-3.5-turbo-16k-0613"),
    /**
     * gpt-3.5-turbo-1106 最新的 GPT-3.5 Turbo 模型具有改进的指令跟踪、JSON 模式、可重现的输出、并行函数调用等。
     */
    GPT_3_5_TURBO_1106("gpt-3.5-turbo-1106"),
    /**
     * GPT4.0
     */
    GPT_4("gpt-4"),
    /**
     * 临时模型，不建议使用，2023年9 月 13 日将被弃用
     */
    @Deprecated
    GPT_4_0314("gpt-4-0314"),
    /**
     * GPT4.0 超长上下文
     */
    GPT_4_32K("gpt-4-32k"),
    /**
     * 临时模型，不建议使用，2023年9 月 13 日将被弃用
     */
    @Deprecated
    GPT_4_32K_0314("gpt-4-32k-0314"),

    /**
     * gpt-4-0613，支持函数
     */
    GPT_4_0613("gpt-4-0613"),
    /**
     * gpt-4-0613，支持函数
     */
    GPT_4_32K_0613("gpt-4-32k-0613"),
    /**
     * 支持数组模式，支持function call，支持可重复输出
     */
    GPT_4_1106_PREVIEW("gpt-4-1106-preview"),
    /**
     * 支持图片
     */
    GPT_4_VISION_PREVIEW("gpt-4-vision-preview"),
    ;
    private final String name;
}
