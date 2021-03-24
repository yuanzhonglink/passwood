package com.pass.api.spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

/**
 * 对于Bean实现 InitializingBean，它将运行 afterPropertiesSet()在所有的 bean 属性被设置之后。
 * 对于 Bean 实现了DisposableBean，它将运行 destroy()在 Spring 容器释放该 bean 之后。
 *
 * @PostConstruct说明
 * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次，类似于Servlet的init()方法。
 * 被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。
 *
 * @PreDestroy说明
 * 被@PreDestroy修饰的方法会在服务器卸载Servlet的时候运行，并且只会被服务器调用一次，类似于Servlet的destroy()方法。
 * 被@PreDestroy修饰的方法会在destroy()方法之后运行，在Servlet被彻底卸载之前。
 *
 * @author yuanzhonglin
 * @date 2021/3/23
 */
public class IniAndDisBeanUtils implements InitializingBean, DisposableBean {
    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void destroy() throws Exception {

    }

    @PostConstruct
    public void init() {

    }

}
