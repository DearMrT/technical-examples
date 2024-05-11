package com.mrt.openai.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mrt.openai.enums.Model;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Mr.T
 * @Date: 2024/5/11
 */
@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class BaseChatCompletion implements Serializable {

    @NonNull
    @Builder.Default
    private String model = Model.GPT_3_5_TURBO.getName();

    /**
     * 指定模型必须输出的格式的对象。
     *
     * @since 1.1.2
     */
    @JsonProperty("response_format")
    private ResponseFormat responseFormat;

    /**
     * 使用什么取样温度，0到2之间。较高的值(如0.8)将使输出更加随机，而较低的值(如0.2)将使输出更加集中和确定。
     * <p>
     * We generally recommend altering this or but not both.top_p
     */
    @Builder.Default
    private double temperature = 0.2;

    /**
     * 使用温度采样的替代方法称为核心采样，其中模型考虑具有top_p概率质量的令牌的结果。因此，0.1 意味着只考虑包含前 10% 概率质量的代币。
     * <p>
     * 我们通常建议更改此设置，但不要同时更改两者。temperature
     */
    @JsonProperty("top_p")
    @Builder.Default
    private Double topP = 1d;


    /**
     * 为每个提示生成的完成次数。
     */
    @Builder.Default
    private Integer n = 1;


    /**
     * 是否流式输出.
     * default:false
     */
    @Builder.Default
    private boolean stream = false;
    /**
     * 停止输出标识
     */
    private List<String> stop;
    /**
     * 最大支持4096
     */
    @JsonProperty("max_tokens")
    @Builder.Default
    private Integer maxTokens = 2048;


    @JsonProperty("presence_penalty")
    @Builder.Default
    private double presencePenalty = 0;

    /**
     * -2.0 ~~ 2.0
     */
    @JsonProperty("frequency_penalty")
    @Builder.Default
    private double frequencyPenalty = 0;


    /**
     * 用户唯一值，确保接口不被重复调用
     */
    private String user;

    /**
     * @since 1.1.2
     */
    private Integer seed;
}
