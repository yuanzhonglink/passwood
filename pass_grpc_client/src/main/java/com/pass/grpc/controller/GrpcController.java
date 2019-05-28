package com.pass.grpc.controller;

import com.pass.grpc.client.HelloWorldClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuanzhonglin
 * @date 2019/5/28
 */
@RestController
@Slf4j
@RequestMapping("/grpc")
public class GrpcController {

    @Autowired
    private HelloWorldClient helloWorldClient;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String sayHello() {
        return helloWorldClient.sayHello();
    }
}
