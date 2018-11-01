package com.yz.springboot.entity;

/**
 * @author huiRan
 * @date 2018/9/17
 */
public class LoginBean {

    private String accessToken;
    private TUser user;

    public LoginBean(String accessToken, TUser user) {
        this.accessToken = accessToken;
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public TUser getUser() {
        return user;
    }
}
