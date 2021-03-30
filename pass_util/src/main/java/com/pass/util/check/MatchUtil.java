package com.pass.util.check;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Matcher使用
 *
 * @author yuanzhonglin
 * @date 2021/3/24
 */
public class MatchUtil {

    public static void main(String[] args) throws Exception{
        check();
    }

    /*
        匹配除特定字符（自定义）以外的内容
     */
    public static void check() {
        Pattern ROUTE_PATTERN = Pattern.compile("([&!=,]*)\\s*([^&!=,\\s]+)");
        String rule = "host != 192.168.32.16, 192.168.65.23 & project != projecta";
        Matcher matcher = ROUTE_PATTERN.matcher(rule);
        while (matcher.find()) {
            System.out.println("获取匹配的全部内容：" + matcher.group(0));
            System.out.println("获取第一组`([&!=,]*)`的匹配：" + matcher.group(1));
            System.out.println("获取第二组`([^&!=,\\s]+)`的匹配：" + matcher.group(2));

            System.out.println("-------------------------------------");
        }
    }
}
