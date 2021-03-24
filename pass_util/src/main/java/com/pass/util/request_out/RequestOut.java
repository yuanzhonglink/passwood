package com.pass.util.request_out;




import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yuanzhonglin
 * @date 2021/3/23
 */
@Slf4j
public class RequestOut {
    public static void out(HttpServletRequest request){
        /**
         * 1.获得客户机信息
         */
        String requestUrl = request.getRequestURL().toString();//得到请求的URL地址
        String requestUri = request.getRequestURI();//得到请求的资源
        String queryString = request.getQueryString();//得到请求的URL地址中附带的参数
        String remoteAddr = request.getRemoteAddr();//得到来访者的IP地址
        String remoteHost = request.getRemoteHost();
        int remotePort = request.getRemotePort();
        String remoteUser = request.getRemoteUser();
        String method = request.getMethod();//得到请求URL地址时使用的方法
        String pathInfo = request.getPathInfo();
        String localAddr = request.getLocalAddr();//获取WEB服务器的IP地址
        String localName = request.getLocalName();//获取WEB服务器的主机名
        log.info("获取到的客户机信息如下：");
        log.info("<hr/>");
        log.info("请求的URL地址："+requestUrl);
        log.info("<br/>");
        log.info("请求的资源："+requestUri);
        log.info("<br/>");
        log.info("请求的URL地址中附带的参数："+queryString);
        log.info("<br/>");
        log.info("来访者的IP地址："+remoteAddr);
        log.info("<br/>");
        log.info("来访者的主机名："+remoteHost);
        log.info("<br/>");
        log.info("使用的端口号："+remotePort);
        log.info("<br/>");
        log.info("remoteUser："+remoteUser);
        log.info("<br/>");
        log.info("请求使用的方法："+method);
        log.info("<br/>");
        log.info("pathInfo："+pathInfo);
        log.info("<br/>");
        log.info("localAddr："+localAddr);
        log.info("<br/>");
        log.info("localName："+localName);
    }
}
