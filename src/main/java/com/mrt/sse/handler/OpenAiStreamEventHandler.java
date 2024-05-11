package com.mrt.sse.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrt.openai.bean.ChatChoice;
import com.mrt.openai.bean.ChatCompletionResponse;
import com.mrt.openai.enums.Model;
import com.mrt.openai.util.TikTokensUtil;
import com.mrt.sse.listener.AbstractStreamEventSource;
import com.mrt.sse.service.CustomService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

/**
 * @Author: Mr.T
 * @Date: 2024/5/11
 */
@Slf4j
public class OpenAiStreamEventHandler extends AbstractStreamEventSource {


    private String completed = "[DONE]";

    protected CustomService customService;

    private StringBuilder stringBuilder = new StringBuilder();

    public OpenAiStreamEventHandler(SseEmitter sseEmitter, CustomService customService) {
        super(sseEmitter);
        this.customService = customService;
    }


    public OpenAiStreamEventHandler(SseEmitter sseEmitter) {
        super(sseEmitter);
    }

    @Override
    protected void handleEvent(String type, String data) throws Exception {
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
        stringBuilder.append(content);
    }


    @Override
    public void onFailure(@NotNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
        try {
            log.error("请求服务器失败: {},原因:{}", t,response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onClosed(@NotNull EventSource eventSource) {
        log.info("连接关闭");
        // 后置业务处理
        customService.handle(new Object());
        int tokens = TikTokensUtil.tokens(Model.GPT_3_5_TURBO.getName(), stringBuilder.toString());
        log.info("消耗的token数量为:{}",tokens);;
    }

    @Override
    public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
        log.info("打开连接");
    }
}
