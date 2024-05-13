package com.mrt.gml.handler;

import com.mrt.sse.listener.AbstractStreamEventSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 *  智谱清言的 SSE 流式处理器
 * @Author: Mr.T
 * @Date: 2024/5/13
 */
@Slf4j
public class GmlStreamEventHandler  extends AbstractStreamEventSource {
    public GmlStreamEventHandler(SseEmitter sseEmitter) {
        super(sseEmitter);
    }
}
