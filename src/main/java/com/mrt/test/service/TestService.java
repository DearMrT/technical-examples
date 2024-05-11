package com.mrt.test.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.mrt.openai.bean.BaseMessage;
import com.mrt.openai.bean.ChatCompletion;
import com.mrt.openai.bean.Message;
import com.mrt.sse.CustormSseEmitter;
import com.mrt.sse.handler.OpenAiStreamEventHandler;
import com.mrt.sse.service.CustomService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSources;
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
    private OkHttpClient okHttpClient;


    public SseEmitter chat(String message){
        String openAiUrl = "https://api.openai.one/v1/chat/completions";
        Message message1 = Message.builder().role(BaseMessage.Role.USER).content(message).build();

        List<Message> messages = new ArrayList<>();
        // AI 角色设定
        messages.add(message1);
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .stream(true)
                .model("gpt-3.5-turbo").messages(messages).build();


        Request request = new Request.Builder()
                .url(openAiUrl)
                .addHeader("Authorization", "Bearer sk-636dfa1ac633b5a71a48c3c17a5707133bf62b035d324b1e")
                .post(RequestBody.create(JSON.toJSONString(chatCompletion),MediaType.get("application/json")))
                .build();
        // 3分钟超时
        SseEmitter sseEmitter = new CustormSseEmitter(180 * 1000L);
        OpenAiStreamEventHandler streamEventHandler = new OpenAiStreamEventHandler(sseEmitter, this);
        EventSource eventSource = EventSources.createFactory(okHttpClient).newEventSource(request, streamEventHandler);
        eventSource.request();
        return sseEmitter;
    }

    @Override
    public void handle(Object obj) {
        System.out.println("爱干嘛干嘛!!!");
    }
}
