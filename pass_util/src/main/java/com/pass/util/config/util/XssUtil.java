package com.pass.util.config.util;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * 功能描述: 校验参数、参数值
 *
 * @auther: yuanzhonglin
 * @date: 2018/10/23 11:12
 */
@Slf4j
public class XssUtil {

    // 正则——特殊字符
    private static final String SPECIAL_CHAR = "(\\s|\\S)*([`~!#%^&*()+=|';'<>?~！#￥%……&*（）+|{}【】‘；：”“’])(\\s|\\S)*";
    // 正则——关键字
    private static final String KEYWORD = "(\\s|\\S)*(insert|select)(\\s|\\S)*";
    // 正则
    private static final String IS_NUM = "(\\s|\\S)*(\\d)(\\s|\\S)*";
    // 正则
    private static final String REGEX_PARAM = "[a-zA-Z]+\\[(\\d+)\\](\\s|\\S)*";

    public static final String REGEX_FIVE = "(\\s|\\S)*(.com)(\\s|\\S)*";

    // 校验参数值
    public static boolean judgeParamValue(String str) {
        boolean flag = false;
        String text = str.toLowerCase();

        if (!text.matches(SPECIAL_CHAR)) {
            if (!text.matches(KEYWORD)) {
                flag = true;
            }
        }

        return flag;
    }

    // 校验参数
    public static boolean judgeParam(String str) {
        boolean flag = false;
        if (!str.matches(IS_NUM)||str.matches(REGEX_PARAM)) {
            flag = true;
        }
        return flag;
    }
}
