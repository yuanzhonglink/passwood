package com.pass.ftl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * freemarker使用
 *
 * @author yuanzhonglin
 * @date 2019/11/26
 */
@Controller
@RequestMapping("/ftl")
public class FtlController {

    @RequestMapping("/index")
    public String index(ModelMap map) {
        map.addAttribute("host","哈,比");
        return "index";
    }
}
