package com.mrt.test.controller;

import com.mrt.test.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping("/chat")
    public SseEmitter chat(){
        return testService.chat("帮我写五言绝句首诗");
    }
}
