package com.pass.prometheus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yuanzhonglin
 * @date 2020/7/31
 * @Description:
 */
@RestController
@RequestMapping("/rec")
public class RecController {

    @RequestMapping(value = "/demo", method = {RequestMethod.POST})
    public void demo(HttpServletRequest request) throws Exception {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String str;
        while ((str = reader.readLine()) != null) {
            sb.append(str);
        }
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("---------"+ sd.format(new Date()) +"----------");
        System.out.println(sb.toString());
        System.out.println("-------------------");
    }
}
