package com.pass.consul.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author yuanzhonglin
 * @date 2019/5/15
 * @Description:
 */
@RestController
@RequestMapping("/consul")
public class ConsulController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.port}")
    private String port;

    @RequestMapping("/health")
    public String hello() {
        return "helle consul--server";
    }

    @RequestMapping(value = "/method_1", method = RequestMethod.GET)
    public String method_1() {
        return "hello consul-server-" + port;
    }

    @RequestMapping(value = "/method_2", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "fallback")
    public String method_2() {
        String object = restTemplate.getForObject("http://consul-three/consul/method_2", String.class);
        return object;
    }

    private String fallback() {
        return "fallback";
    }
}
