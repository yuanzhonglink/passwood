package com.pass.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author yuanzhonglin
 * @date 2021/4/5
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
@Slf4j
public class SonarControllerTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Test
    public void testHello() throws Exception {
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("num", "3");
        String string = mvc.perform(get("/sonar/api1").params(paramMap)).andReturn().getResponse().getContentAsString();
        log.info("---------------------" + string);
    }

}
