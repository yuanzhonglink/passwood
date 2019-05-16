package com.pass.consul.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuanzhonglin
 * @date 2019/5/15
 * @Description:
 */
@RestController
@RequestMapping("/consul")
public class ConsulController {

    @Value("${server.port}")
    private String port;

    @RequestMapping("/health")
    public String hello() {
        return "helle consul--server";
    }

    @RequestMapping(value = "/demo1", method = RequestMethod.GET)
    public String demo1() {
        return "consul-server-demo1-" + port;
    }

    @RequestMapping(value = "/demo2", method = RequestMethod.GET)
    public String demo2() {
        return "consul-server-demo2-" + port;
    }
}
