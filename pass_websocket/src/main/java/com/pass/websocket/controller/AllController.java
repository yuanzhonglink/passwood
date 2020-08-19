package com.pass.websocket.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yuanzhonglin
 * @date 2020/7/31
 * @Description:
 */
@RestController
@RequestMapping("/all")
public class AllController {

    private ExecutorService sendExecutor;
    @Autowired
    private AmqpTemplate amqpTemplate;

    public AllController() {
        sendExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
    }

    @RequestMapping(value = "/rec", method = {RequestMethod.POST})
    public void receive(@RequestBody String content) throws Exception {
        System.out.println("--------------------------------------");
        sendExecutor.submit(() -> {
            amqpTemplate.convertAndSend("resource.alarm.queue", content);
        });
    }
}
