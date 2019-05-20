package com.pass.util.config.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 参数、参数值过滤
 *
 * @author yuanzhonglin
 * @date 2019/5/20
 */
//@WebFilter(filterName = "xssFilter", urlPatterns = {"/*"})
//@Order(1) //如果使用这种方式记得在启动类上加@ServletComponentScan
public class XssFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("xssFilter，初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 处理

        // 转给下一个过滤器
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("xssFilter，销毁");
    }
}
