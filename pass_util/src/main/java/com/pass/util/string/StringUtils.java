package com.pass.util.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yuanzhonglin
 * @date 2019/5/20
 * @Description:
 */
public final class StringUtils {
    public StringUtils() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean hasText(String str) {
        return hasText((CharSequence)str);
    }

    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        } else {
            int strLen = str.length();

            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        }
    }

    private static boolean hasLength(CharSequence str) {
        return str != null && str.length() > 0;
    }

    public static boolean hasChineseCharacter(String str) {
        String reg = "[\\u4E00-\\u9FA5]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static String trim(String source) {
        return isEmpty(source) ? source : source.trim();
    }
}
