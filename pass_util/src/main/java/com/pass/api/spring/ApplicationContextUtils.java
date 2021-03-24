package com.pass.api.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * ApplicationContextAware使用
 * 当一个类实现了这个接口之后，这个类就可以方便地获得ApplicationContext中的所有bean。
 * 换句话说，就是这个类可以直接获取Spring配置文件中，所有有引用到的bean对象
 *
 * @Author yuanzhonglin
 * @since 2021/3/23
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException {
        ApplicationContextUtils.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 获取被某个注解修饰的类
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> clazz) throws BeansException {
        return applicationContext.getBeansWithAnnotation(clazz);
    }

}
