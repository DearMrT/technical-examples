package com.mrt.openai.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseMessage implements Serializable {

    /**
     * 目前支持四个中角色参考官网，进行情景输入：
     * https://platform.openai.com/docs/guides/chat/introduction
     */
    private String role;


    private String name;







    /**
     * 构造函数
     *
     * @param role         角色
     * @param name         name
     */
    public BaseMessage(String role, String name) {
        this.role = role;
        this.name = name;
    }

    public BaseMessage() {
    }


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

}
