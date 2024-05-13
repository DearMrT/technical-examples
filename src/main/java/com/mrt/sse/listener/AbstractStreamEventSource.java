package com.mrt.sse.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrt.openai.bean.ChatChoice;
import com.mrt.openai.bean.ChatCompletionResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

/**
 * @Author: Mr.T
 * @Date: 2024/5/11
 */
@Slf4j
public abstract class AbstractStreamEventSource extends EventSourceListener {

    protected SseEmitter  sseEmitter;

    //protected boolean completed = false;

    private String completed = "[DONE]";


    public AbstractStreamEventSource(SseEmitter sseEmitter) {
        this.sseEmitter = sseEmitter;
    }


    @Override
    public void onEvent(@NotNull EventSource eventSource, @Nullable String id, @Nullable String type, @NotNull String data) {
        //if (!completed) {
        //    return;
        //}
        try {
            handleEvent(type,data);
        } catch (Exception e) {
            log.error("业务侧处理数据异常，原因",e);
             // 因为 open ai 返回的数据格式 不是标准的，所以不能直接关闭
            //sseEmitter.complete();
        }

    }

    protected  void handleEvent(String type, String data) throws Exception {
            // 如果是结束符
            if (completed.equals(data)) {
                sseEmitter.send(SseEmitter.event().name("message").data(data));
                sseEmitter.complete();
                return;
            }
            ChatCompletionResponse response = new ObjectMapper().reader().readValue(data,ChatCompletionResponse.class);
            List<ChatChoice> choices = response.getChoices();
            String content = choices.get(0).getDelta().getContent();
            log.info("返回的数据为:{}",data);
            sseEmitter.send(content);
        }
}
