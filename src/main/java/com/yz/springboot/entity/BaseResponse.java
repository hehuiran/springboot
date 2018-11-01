package com.yz.springboot.entity;

/**
 * @author huiRan
 * @date 2018/9/4
 */
public class BaseResponse<T> {

    private Integer code;
    private String msg;
    private T data;

    public BaseResponse(T data) {
        this.data = data;
        this.code = 0;
        this.msg = "SUCCESS";
    }

    public BaseResponse() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
