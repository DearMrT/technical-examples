package com.mrt.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mrt.test.service.TestService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;

/**
 * @Author: Mr.T
 * @Date: 2024/5/11
 */
@RequestMapping("/test")
@RestController
public class TestController {

    @Resource
    private TestService testService;

    @RequestMapping(value = "/chat", method = RequestMethod.POST, produces = "text/event-stream;charset=UTF-8")
    public SseEmitter chat() throws JsonProcessingException {
        return testService.chat("你好");
    }
}
