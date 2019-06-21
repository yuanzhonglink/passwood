package com.pass.curator.util;


public class StringUtils {
    /**
     * 判断字符串是否为空
     *
     * @author Shawpin Shi
     * @since 2016年12月9日
     */
    public static boolean isEmpty(String text) {
        if (text == null || text.length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否为非空
     *
     * @author Shawpin Shi
     * @since 2018/1/8
     */
    public static boolean isNotEmpty(String text) {
        return !isEmpty(text);
    }

}
