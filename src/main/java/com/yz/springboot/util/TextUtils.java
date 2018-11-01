package com.yz.springboot.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huiRan
 * @date 2018/9/4
 */
public final class TextUtils {

    private TextUtils() {
        throw new UnsupportedOperationException("u can't initialize me");
    }

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");

    public static boolean isEmpty(CharSequence text) {
        return text == null || text.length() == 0;
    }

    public static boolean isNumeric(String s){
        Matcher isNum = NUMBER_PATTERN.matcher(s);
        return isNum.matches();
    }

    public static String getRandomString(int length) {
        StringBuilder builder = new StringBuilder("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int range = builder.length();
        for (int i = 0; i < length; i++) {
            sb.append(builder.charAt(random.nextInt(range)));
        }
        return sb.toString();
    }
}
