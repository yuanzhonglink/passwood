package com.pass.api.lambda;

import java.util.function.Consumer;

/**
 * @author yuanzhonglin
 * @date 2019/6/18
 */
public class ConsumerDemo {

    public static <T> void test(T t, Consumer<T> consumer) {
        consumer.accept(t);
    }
}
