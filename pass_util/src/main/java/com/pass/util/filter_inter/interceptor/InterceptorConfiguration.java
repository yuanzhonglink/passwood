package com.pass.util.filter_inter.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 * 原理：
 *  1.所有的拦截器(Interceptor)和处理器(Handler)都注册在HandlerMapping中。
 *  2.Spring MVC中所有的请求都是由DispatcherServlet分发的。
 *  3.当请求进入DispatcherServlet.doDispatch()时候，首先会得到处理该请求的
 *  Handler（即Controller中对应的方法）以及所有拦截该请求的拦截器。拦截器就是在这里被调用开始工作的。
 *
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
