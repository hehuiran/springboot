package com.yz.springboot.constant;

/**
 * @author huiRan
 * @date 2018/9/4
 */
public interface Constant {

    /**
     * 默认page
     */
    int DEFAULT_PAGE = 1;
    /**
     * 默认单页返回的条数
     */
    int DEFAULT_PAGE_SIZE = 20;
    /**
     * 单页返回最大的条数
     */
    int MAX_PAGE_SIZE = 100;
    /**
     * 单页返回最小的条数
     */
    int MIN_PAGE_SIZE = 1;

    /**
     * 当前用户参数名
     */
    String CURRENT_USER = "current_user";

    String AUTHORIZATION = "Authorization";


    /**
     * 与毫秒的倍数
     */
    int MSEC = 1;
    int SEC  = 1000;
    int MIN  = 60000;
    int HOUR = 3600000;
    int DAY  = 86400000;
}
