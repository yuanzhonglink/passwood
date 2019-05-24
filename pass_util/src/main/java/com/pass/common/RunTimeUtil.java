package com.pass.common;

/**
 *
 *
 * @Author yuanzhonglin
 * @since 2019/5/24
 */
public class RunTimeUtil {

    private static volatile Runtime runtime = null;

    public static Runtime getRunTimeUtil(){
        if(runtime == null){
            synchronized (Runtime.class){
                if(runtime == null){
                    runtime = Runtime.getRuntime();
                }
            }
        }
        return runtime;
    }

    private RunTimeUtil() {
        throw new IllegalStateException("Utility class");
    }
}
