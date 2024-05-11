package com.mrt.openai.bean;

import lombok.*;

/**
 * @Author: Mr.T
 * @Date: 2024/5/11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFormat {
    /**
     * 默认：text
     *
     * @see Type
     */
    private String type;

    @Getter
    @AllArgsConstructor
    public enum Type {
        /**
         * JSON格式 使用JSON 格式时要求问题里面要求是JSON格式  且注意 模型
         */
        JSON_OBJECT("json_object"),
        /**
         * 文本格式
         */
        TEXT("text"),
        ;
        private final String name;
    }
}
