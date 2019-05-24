package com.pass.util.filter_inter.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置类
 *
 * @author yuanzhonglin
 * @date 2019/5/20
 */
@Configuration
public class FilterConfig {

    // xss过滤器
    @Bean
    public FilterRegistrationBean xssFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("XssFilter");
        registration.setOrder(1);

        return registration;
    }

    // 自定义过滤器

}
