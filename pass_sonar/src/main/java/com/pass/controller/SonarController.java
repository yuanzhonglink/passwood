package com.pass.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuanzhonglin
 * @date 2021/4/5
 */
@RestController
@RequestMapping("/sonar")
public class SonarController {

    @GetMapping("/api1")
    public String api1(int num) {
        if (num > 0) {
            return "成功";
        } else {
            return "失败";
        }
    }
}
