package com.mrt.openai.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mrt.openai.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Mr.T
 * @Date: 2024/5/11
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class Message extends BaseMessage implements Serializable {

    /**
     * 旧的content属性仅仅支持字符类型
     */
    private String content;

    public static Builder builder() {
        return new Builder();
    }

    /**
     * 构造函数
     *
     * @param role         角色
     * @param name         name
     * @param content      content
     */
    public Message(String role, String name, String content) {
        this.content = content;
        super.setRole(role);
        super.setName(name);
    }

    public Message() {
    }

    private Message(Builder builder) {
        setContent(builder.content);
        super.setRole(builder.role);
        super.setName(builder.name);
    }

    public static final class Builder {
        private String role;
        private String content;
        private String name;

        public Builder() {
        }

        public Builder role(Role role) {
            this.role = role.getName();
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }





        public Message build() {
            return new Message(this);
        }
    }
}
