package com.pass.util.check;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验工具
 *
 * @author yuanzhonglin
 * @date 2021/3/23
 */
@Slf4j
public class CheckUtil {


    /**
     * 手机号校验
     */
    public static boolean phoneCheck(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            log.info("手机号应为11位数");
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
                log.info("请填入正确的手机号");
            }
            return isMatch;
        }
    }

    /*
     * 时间校验
     *
     * 假设参数time为2018-11-13 14:39:00 错误率,连续超过1分钟,小于100百分比,达到0百分比
     */
    public static String timeCheck(String time) {
        String format = "((2)[0-9]{3})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) "
                + "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
        time = time.replaceAll(format,"");
        return time;
    }
}
