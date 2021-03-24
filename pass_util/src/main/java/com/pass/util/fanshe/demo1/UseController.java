package com.pass.util.fanshe.demo1;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author yuanzhonglin
 * @date 2021/3/23
 */
public class UseController {
    public static void main(String[] args) {
        getMethod();
    }

    public static void getType() {
        //设置扫描哪个我的annotation包下的类，可以扫描全部项目包，包括引用的jar
        Reflections reflections = getFullReflections("com.pass.util.fanshe");
        //获取带MQConsumer注解的类
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(MQConsumer.class);
        for (Class c:typesAnnotatedWith){
            //通过循环打印出权限定类名
            System.out.println(c.getName());
        }
    }

    public static void getMethod() {
        Reflections reflections = getFullReflections("com.pass.util.fanshe");
        //获取带MQConsumer注解的类
        Set<Method> methodsAnnotatedWith = reflections.getMethodsAnnotatedWith(MQMethod.class);
        for (Method m : methodsAnnotatedWith){
            //通过循环打印出权限定类名
            System.out.println(m.getDeclaringClass() + "." + m.getName());
        }
    }

    /**
     * 如果没有配置scanner，默认使用SubTypesScanner和TypeAnnotationsScanner
     * @param basePackage 包路径
     */
    private static Reflections getFullReflections(String basePackage){
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.addUrls(ClasspathHelper.forPackage(basePackage));
        builder.setScanners(new TypeAnnotationsScanner(), new SubTypesScanner(),
                new MethodAnnotationsScanner(), new FieldAnnotationsScanner());
        builder.filterInputsBy(new FilterBuilder().includePackage(basePackage));

        Reflections reflections = new Reflections(builder);
        return reflections;
    }
}
