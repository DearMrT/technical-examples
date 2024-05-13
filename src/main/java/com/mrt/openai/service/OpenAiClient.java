package com.mrt.openai.service;

import cn.hutool.http.ContentType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrt.openai.bean.BaseChatCompletion;
import com.mrt.openai.common.AiRequestHolder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.sse.EventSources;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author: Mr.T
 * @Date: 2024/5/11
 */
@Slf4j
@Component
public class OpenAiClient {

    private  String apiHost = "https://api.openai.one/";

    @Resource
    private OkHttpClient okHttpClient;


    public   <T extends BaseChatCompletion> void openAiStreamChatCompletion(AiRequestHolder holder) {
        if (Objects.isNull(holder.getStreamEventSource())) {
            log.error("参数异常：EventSourceListener不能为空，可以参考：com.unfbx.chatgpt.sse.ConsoleEventSourceListener");
            throw new RuntimeException("参数异常：EventSourceListener不能为空，可以参考：com.unfbx.chatgpt.sse.ConsoleEventSourceListener");
        }
        if (!holder.getChatCompletion().isStream()) {
            holder.getChatCompletion().setStream(true);
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(holder.getChatCompletion());
            log.info("请求数据为:{}",requestBody);
            Request request = new Request.Builder()
                    .url(apiHost + "v1/chat/completions")
                    .addHeader("Authorization",holder.getKey())
                    .post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()), requestBody))
                    .build();
            //创建事件
            EventSources.createFactory(okHttpClient).newEventSource(request, holder.getStreamEventSource());
        } catch (JsonProcessingException e) {
            log.error("请求参数解析异常：{}", e);
            e.printStackTrace();
        } catch (Exception e) {
            log.error("请求参数解析异常：{}", e);
            e.printStackTrace();
        }
    }
}
