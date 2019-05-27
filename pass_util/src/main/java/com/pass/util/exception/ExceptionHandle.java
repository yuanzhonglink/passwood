package com.pass.util.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 * 异常拦截<br>
 * 1.通过加入 @RestControllerAdvice 来声明该类可拦截 Controller 请求
 * 2.同时在handle方法加入 @ExceptionHandler 并在该注解中指定要拦截的异常类
 * 3.Controller层无需处理异常，只需抛出
 * </p>
 *
 * @author yuanzhonglin
 * @date 2019/5/27
 */
@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class) // 捕获 Controller 中抛出的指定类型的异常，也可以指定其他异常
    public <E>Message<E> handler(Exception exception) {

        // 这里只对自定义异常以及未知异常进行处理，如果你在某方法中明确知道可能会抛出某个异常，可以加多一个特定的处理。比如说你明确知道该方法可能抛出 NullPointException 可以追加 NullPointException 的处理：
        if (exception instanceof IndexOutOfBoundsException) {
            return MessageUtil.error(500, "数组下标越界！");
        } else if (exception instanceof NullPointerException) {
            return MessageUtil.error(500, "空指针异常信！");
        } else {
            return MessageUtil.error(120, "异常信息：" + exception.getMessage());
        }
    }

}
