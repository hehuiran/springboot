package com.yz.springboot.exception;

/**
 * @author huiRan
 * @date 2018/9/4
 */
public enum ErrorEnum {

    /**
     * 未知错误
     */
    UNKNOWN_ERROR(101, "未知错误"),

    /**
     * 用户不存在
     */
    USER_NOT_EXIST_ERROR(102, "用户不存在,请重新登录"),

    /**
     * 页码错误(page>=1)
     */
    PAGE_ERROR(103, "页码错误"),

    /**
     * 单页数据条数错误(pageSize>=1&&pageSize<=100)
     */
    PAGE_SIZE_ERROR(104, "单页数据条数错误"),

    /**
     * 上传的文件为空
     */
    FILE_NULL_ERROR(105, "上传的文件为空"),

    /**
     * 文件格式错误
     */
    FILE_FORMAT_ERROR(106, "文件格式错误"),

    /**
     * 解析excel失败
     */
    PARSE_EXCEL_ERROR(107, "解析excel失败"),

    /**
     * token为null,请设置token
     */
    TOKEN_IS_NULL_ERROR(108, "token为null,请设置token"),

    /**
     * token失效,请重新登录
     */
    TOKEN_FAILURE_ERROR(109, "token失效,请重新登录"),

    /**
     * token错误,请重新登录
     */
    TOKEN_ERROR(110, "token错误,请重新登录"),

    /**
     * user对象丢失
     */
    LOSE_USER_ERROR(111, "user对象丢失"),

    /**
     * 手机格式错误
     */
    MOBILE_FORMAT_ERROR(112, "手机格式错误"),

    /**
     * 密码格式错误
     */
    PASSWORD_FORMAT_ERROR(113, "密码格式错误"),

    /**
     * 密码错误
     */
    PASSWORD_ERROR(114, "密码错误"),

    /**
     * 该手机已被注册
     */
    MOBILE_EXIST_ERROR(115, "该手机已被注册"),

    /**
     * 注册失败
     */
    REGISTER_ERROR(116, "注册失败"),

    /**
     * 修改失败
     */
    MODIFY_ERROR(117, "修改失败"),
    ;


    private Integer code;
    private String msg;

    ErrorEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
