package com.pass.util.config.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.pass.util.config.util.XssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * 参数拦截
 *
 * @Author yuanzhonglin
 * @since 2019/5/20
 */
@Slf4j
@Component
public class XssInterceptor extends HandlerInterceptorAdapter {

    /**
     * 预处理回调方法，实现处理器的预处理（如登录检查）
     * 第三个参数为响应的处理器，即controller
     * 返回true，表示继续流程，调用下一个拦截器或者处理器
     * 返回false，表示流程中断，通过response产生响应
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        //参数名校验
        Enumeration enu = request.getParameterNames();
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
}
