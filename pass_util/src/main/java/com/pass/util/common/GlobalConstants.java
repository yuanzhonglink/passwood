package com.pass.util.common;

import com.pass.util.string.StringUtils;

import java.util.Properties;

/**
 * 系统常量
 *
 * @author yuanzhonglin
 * @date 2019/5/21
 */
public class GlobalConstants {
    /**
     * 系统属性
     */
    public static final Properties SYSTEM_PROPERTIES = System.getProperties();

    /**
     * 操作系统名称
     */
    public static final String OS_NAME = SYSTEM_PROPERTIES.getProperty("os.name");

    /**
     * 操作系统是否为Windows
     */
    public static final boolean IS_WINDOWS = checkWindowsOS();
    private static boolean checkWindowsOS() {
        if (StringUtils.isNotEmpty(OS_NAME)) {
            if (OS_NAME.toLowerCase().contains("windows")) {
                return true;
            }
        }
        return false;
    }

}
