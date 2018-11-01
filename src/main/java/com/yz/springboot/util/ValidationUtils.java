package com.yz.springboot.util;

import com.yz.springboot.exception.CommonException;
import com.yz.springboot.exception.ErrorEnum;

import java.util.regex.Pattern;

import static com.yz.springboot.constant.Constant.*;

/**
 * @author huiRan
 * @date 2018/9/4
 */
public final class ValidationUtils {

    private ValidationUtils() {
        throw new UnsupportedOperationException("u can't initialize me");
    }


    /**
     * 正则：手机号（精确）
     * <p>移动：134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188</p>
     * <p>联通：130、131、132、145、155、156、171、175、176、185、186</p>
     * <p>电信：133、153、173、177、180、181、189</p>
     * <p>全球星：1349</p>
     * <p>虚拟运营商：170</p>
     */
    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,1,3,5-8])|(18[0-9])|(147))\\d{8}$";
    public static final int PASSWORD_MIN_COUNT = 6;

    public static void checkPage(Integer page, Integer pageSize) {
        if (page == null || page < DEFAULT_PAGE) {
            throw new CommonException(ErrorEnum.PAGE_ERROR);
        }
        if (pageSize == null || pageSize < MIN_PAGE_SIZE || pageSize > MAX_PAGE_SIZE) {
            throw new CommonException(ErrorEnum.PAGE_SIZE_ERROR);
        }
    }

    public static void checkMobile(CharSequence mobile) {
        if (!isMatch(REGEX_MOBILE_EXACT, mobile)) {
            throw new CommonException(ErrorEnum.MOBILE_FORMAT_ERROR);
        }
    }

    public static void checkPassword(CharSequence password) {
        if (TextUtils.isEmpty(password) || password.length() < PASSWORD_MIN_COUNT) {
            throw new CommonException(ErrorEnum.PASSWORD_FORMAT_ERROR);
        }
    }

    /**
     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(final String regex, final CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }
}
