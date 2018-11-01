package com.yz.springboot.util;

import com.yz.springboot.entity.BaseResponse;
import com.yz.springboot.exception.ErrorEnum;

/**
 * @author huiRan
 * @date 2018/9/4
 */
public final class ResponseUtils {

    private ResponseUtils() {
        throw new UnsupportedOperationException("u can't initialize me");
    }

    public static BaseResponse success(Object obj) {
        return new BaseResponse<>(obj);
    }

    public static BaseResponse successNoData() {
        return new BaseResponse<>(null);
    }

    public static BaseResponse error(Integer code, String msg) {
        BaseResponse response = new BaseResponse();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    public static BaseResponse error(ErrorEnum errorEnum) {
        BaseResponse response = new BaseResponse();
        response.setCode(errorEnum.getCode());
        response.setMsg(errorEnum.getMsg());
        return response;
    }

}
