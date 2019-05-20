package com.pass.util.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 *
 * @Author yuanzhonglin
 * @since 2019/5/20
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    
    @Autowired
    private XssInterceptor xssInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        InterceptorRegistration paramIR = registry.addInterceptor(xssInterceptor);
        // 拦截器拦截路径
        paramIR.addPathPatterns("/**");
        // 拦截器排除拦截路径
//        paramIR.excludePathPatterns("/api/upload/fileUpload");
    }
}
