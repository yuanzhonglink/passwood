package com.pass.consul.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;

/**
 * @author yuanzhonglin
 * @date 2019/5/15
 * @Description:
 */
@RestController
@RequestMapping("/consul")
public class ConsulClientController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/health")
    public String checkHealth(){
        return "hello consul--client";
    }

    @RequestMapping(value = "/method_1",method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "fallback")
    public String method_one(){
        List<String> list = discoveryClient.getServices();
        Iterator<String> listIterator = list.iterator();
        while (listIterator.hasNext()) {
            String str = listIterator.next();
            List<ServiceInstance> instances = discoveryClient.getInstances(str);
            Iterator<ServiceInstance> iterator = instances.iterator();
            while (iterator.hasNext()) {
                ServiceInstance next = iterator.next();
                System.out.println(next.getHost());
                System.out.println(next.getMetadata().toString());
                System.out.println(next.getPort());
                System.out.println(next.getServiceId());
                System.out.println(next.getUri().toString());
                System.out.println(next.isSecure());
            }
            System.out.println("---------------------------------------------------------");
        }

        String str = restTemplate.getForObject("http://consul-server/consul/method_1", String.class);
        return str;
    }

    @RequestMapping(value = "/method_2",method = RequestMethod.GET)
    public String get2() {
        return "hello consul-client";
    }

    private String fallback() {
        return "fallback";
    }
}
