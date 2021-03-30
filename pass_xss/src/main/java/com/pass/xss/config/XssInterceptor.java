package com.pass.xss.config;

import com.alibaba.fastjson.JSONObject;
import com.pass.xss.annotation.SkipParameterCheck;
import com.pass.xss.util.XssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;


/**
 * Xss拦截器
 *
 * @author yuanzhonglin
 */
@Slf4j
@Component
public class XssInterceptor extends HandlerInterceptorAdapter {

    private static final String REFERER_NUM = "Referer";

    /**
     * 预处理回调方法，实现处理器的预处理（如登录检查）
     * 第三个参数为响应的处理器，即controller
     * 返回true，表示继续流程，调用下一个拦截器或者处理器
     * 返回false，表示流程中断，通过response产生响应
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        // Referer值校验
        String referer = request.getHeader(REFERER_NUM);
        boolean refererFlag = true;
        // referer为空的情况暂定为不判断 modify by sxp 2019-03-26
        // if((referer == null) || (referer.toLowerCase().matches(XssUtil.REGEX_FIVE))){
        if (referer != null && referer.toLowerCase().matches(XssUtil.REGEX_FIVE)) {
            refererFlag = false;
        }
        if (!refererFlag) {
            returnContent(response,"Referer错误!");
            return false;
        }

        //参数名校验
        Enumeration enu=request.getParameterNames();
        boolean paramFlag = true;
        while(enu.hasMoreElements()){
            String paraName = (String)enu.nextElement();
            if (!XssUtil.judgeParam(paraName)) {
                paramFlag = false;
                break;
            }
        }
        if (!paramFlag) {
            returnContent(response,"参数名包含非法字符!");
            return false;
        }

        // 标记了不检查参数值的接口跳过参数值检查
        if (skipParameterCheck(request, handler)) {
            return true;
        }

        //参数值校验
        boolean paramValueFlag = true;
        Iterator values = request.getParameterMap().values().iterator();
        while (values.hasNext()) {
            paramValueFlag = XssUtil.judgeParamValue(JSONObject.toJSONString(values.next()));
            if (!paramValueFlag) {
                break;
            }
        }
        if (!paramValueFlag) {
            returnContent(response,"输入非法字符!");
            return false;
        }

        return true;
    }

    /**
     * 当前请求进行处理之后，也就是Controller方法调用之后执行，
     * 但是它会在DispatcherServlet 进行视图返回渲染之前被调用。
     * 此时我们可以通过modelAndView对模型数据进行处理或对视图进行处理。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 方法将在整个请求结束之后，也就是在DispatcherServlet渲染了对应的视图之后执行。
     * 这个方法的主要作用是用于进行资源清理工作的。
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
    }

    private void returnContent(HttpServletResponse response, String msg) throws Exception{
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        Map<String, Object> map = new HashMap<>();
        map.put("status", false);
        map.put("message",msg);
        writer.write(JSONObject.toJSONString(map));
    }

    private boolean skipParameterCheck(HttpServletRequest request, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            SkipParameterCheck skipParameterCheck = handlerMethod.getMethodAnnotation(SkipParameterCheck.class);
            if (Objects.nonNull(skipParameterCheck)) {
                log.info("接口[" + request.getRequestURL() + "]跳过参数值合法性检查");
                return true;
            }
            Class<?> clazz = handlerMethod.getBeanType();
            if (Objects.nonNull(AnnotationUtils.findAnnotation(clazz, SkipParameterCheck.class))) {
                log.info("接口[" + request.getRequestURL() + "]跳过参数值合法性检查");
                return true;
            }
        }
        return false;
    }
}
