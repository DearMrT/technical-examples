package com.mrt.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mrt.gml.service.GmlAiClient;
import com.mrt.openai.bean.ChatCompletion;
import com.mrt.openai.bean.Message;
import com.mrt.openai.common.OpenAiRequestHolder;
import com.mrt.openai.enums.Role;
import com.mrt.sse.CustormSseEmitter;
import com.mrt.openai.handler.OpenAiStreamEventHandler;
import com.mrt.sse.service.CustomService;
import com.mrt.openai.service.OpenAiClient;
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

    @Resource
    private GmlAiClient gmlAiClient;


    public SseEmitter chat(String data) throws JsonProcessingException {
        Message message = Message.builder().role(Role.USER).content(data).build();

        List<Message> messages = new ArrayList<>();
        // AI 角色设定
        messages.add(message);
        //ChatCompletion chatCompletion = ChatCompletion.builder()
        //        .stream(Boolean.TRUE)
        //        .model("gpt-3.5-turbo")
        //        .messages(messages)
        //        .temperature(0.2)
        //        .build();

        ChatCompletion chatCompletion = ChatCompletion.builder()
                .stream(Boolean.TRUE)
                .model("glm-4")
                .messages(messages)
                .temperature(0.2)
                .build();
        // 3分钟超时
        SseEmitter sseEmitter = new CustormSseEmitter(180 * 1000L);
        OpenAiStreamEventHandler streamEventHandler = new OpenAiStreamEventHandler(sseEmitter, this);
        // 封装成包装对象 这样便于程序进行扩展
        OpenAiRequestHolder holder = new OpenAiRequestHolder(chatCompletion,streamEventHandler,"Bearer 2d1c6dabcee934dd3a5177b19e995c20.5lQ1zndAv6LoMFAS");
        gmlAiClient.chatGmlStreamChatCompletion(holder);
        return sseEmitter;
    }

    @Override
    public void handle(Object obj) {
        System.out.println("爱干嘛干嘛!!!");
    }
}
