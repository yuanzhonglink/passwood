package com.pass.xss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Exrickx
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    
    @Autowired
    private XssInterceptor xssInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        InterceptorRegistration paramIR = registry.addInterceptor(xssInterceptor);
        paramIR.addPathPatterns("/拦截的接口");
        paramIR.excludePathPatterns("/需要排除的接口");
    }
}
