package com.yz.springboot.exception;

/**
 * @author huiRan
 * @date 2018/9/4
 */
public class CommonException extends RuntimeException {

    private Integer code;
    private String msg;

    public CommonException(ErrorEnum errorEnum) {
        super(errorEnum.getMsg());
        this.code = errorEnum.getCode();
        this.msg = errorEnum.getMsg();
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
