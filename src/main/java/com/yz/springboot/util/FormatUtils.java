package com.yz.springboot.util;

/**
 * @author huiRan
 * @date 2018/9/11
 */
public final class FormatUtils {

    private FormatUtils() {
        throw new UnsupportedOperationException("u can't init me...");
    }

    public static int parseInt(String s) {
        int value;
        try {
            value = Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            value = -1;
        }
        return value;
    }

    public static long parseLong(String s) {
        long value;
        try {
            value = Long.parseLong(s);
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            value = -1;
        }
        return value;
    }

}
