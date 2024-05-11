package com.mrt.sse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.nio.charset.StandardCharsets;

/**
 * @Author: Mr.T
 * @Date: 2024/5/11
 */
public class CustormSseEmitter  extends SseEmitter {
    public CustormSseEmitter() {
        super();
    }

    public CustormSseEmitter(Long timeout) {
        super(timeout);
    }


    @Override
    protected void extendResponse(ServerHttpResponse outputMessage) {
        super.extendResponse(outputMessage);
        HttpHeaders headers = outputMessage.getHeaders();
        headers.setContentType(new MediaType(MediaType.TEXT_EVENT_STREAM, StandardCharsets.UTF_8));
    }
}
