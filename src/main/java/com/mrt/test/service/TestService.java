package com.mrt.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mrt.openai.bean.ChatCompletion;
import com.mrt.openai.bean.Message;
import com.mrt.openai.common.OpenAiRequestHolder;
import com.mrt.openai.enums.Role;
import com.mrt.sse.CustormSseEmitter;
import com.mrt.sse.handler.OpenAiStreamEventHandler;
import com.mrt.sse.service.CustomService;
import com.mrt.sse.OpenAiClient;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr.T
 * @Date: 2024/5/11
 */
@Service
public class TestService implements CustomService {


    @Resource
    private OpenAiClient openAiClient;


    public SseEmitter chat(String data) throws JsonProcessingException {
        Message message = Message.builder().role(Role.USER).content(data).build();

        List<Message> messages = new ArrayList<>();
        // AI 角色设定
        messages.add(message);
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .stream(Boolean.TRUE)
                .model("gpt-3.5-turbo")
                .messages(messages)
                .temperature(0.2)
                .build();
        // 3分钟超时
        SseEmitter sseEmitter = new CustormSseEmitter(180 * 1000L);
        OpenAiStreamEventHandler streamEventHandler = new OpenAiStreamEventHandler(sseEmitter, this);
        // 封装成包装对象 这样便于程序进行扩展
        OpenAiRequestHolder holder = new OpenAiRequestHolder(chatCompletion,streamEventHandler,"Bearer sk-636dfa1ac633b5a71a48c3c17a5707133bf62b035d324b1e");
        openAiClient.openAiStreamChatCompletion(holder);
        return sseEmitter;
    }

    @Override
    public void handle(Object obj) {
        System.out.println("爱干嘛干嘛!!!");
    }
}
