package com.mrt.sse.listener;

import lombok.extern.slf4j.Slf4j;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @Author: Mr.T
 * @Date: 2024/5/11
 */
@Slf4j
public abstract class AbstractStreamEventSource extends EventSourceListener {

    protected SseEmitter  sseEmitter;

    protected boolean completed = false;


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

    protected abstract void handleEvent(String type, String data) throws Exception;
}
