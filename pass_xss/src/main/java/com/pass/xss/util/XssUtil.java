package com.pass.xss.util;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * 功能描述: Xss判断字符
 *
 * @auther: yuanzhonglin
 * @date: 2018/10/23 11:12
 */
@Slf4j
public class XssUtil {

    private static final String REGEX_ONE = "(\\s|\\S)*([_`~!@#$%^&*()+=|';'<>/?~！@#￥%……&*（）+|{}【】‘；：”“’])(\\s|\\S)*";

    private static final String REGEX_TWO = "(\\s|\\S)*(and|exec|execute|insert|count|drop|chr|mid|master|truncate|" +
            "char|sitename|net user|xp_cmdshell|or|like|create|table|from|grant|use|group_concat|" +
            "column_name|information_schema.columns|table_schema|union|where|" +
            "order|by|count|mid|master|truncate|select|delete|update|add)(\\s|\\S)*";


    private static final String REGEX_THREE = "(\\s|\\S)*(\\d)(\\s|\\S)*";

    private static final String REGEX_FOUR = "[a-zA-Z]+\\[(\\d+)\\](\\s|\\S)*";

    public static final String REGEX_FIVE = "(\\s|\\S)*(.com)(\\s|\\S)*";

    public static boolean judgeParam(String str) {
        boolean flag = false;

        Document doc = Jsoup.parse(str);
        String text = doc.text();
        // remove extra white space
        StringBuilder builder = new StringBuilder(text);
        int index = 0;
        while(builder.length()>index){
            char tmp = builder.charAt(index);
            if(Character.isSpaceChar(tmp) || Character.isWhitespace(tmp)){
                builder.setCharAt(index, ' ');
            }
            index++;
        }
        text = builder.toString().replaceAll(" +", " ").trim();
        text = text.toLowerCase();

        if (!text.matches(REGEX_ONE)) {
            if (!text.matches(REGEX_TWO)) {
                flag = true;
            }
        }

        return flag;
    }

    public static boolean judgeParamValue(String str) {
        boolean flag = false;
        String text = str.toLowerCase();

        if (!text.matches(REGEX_ONE)) {
            if (!text.matches(REGEX_TWO)) {
                flag = true;
            }
        }
        return flag;
    }
}
