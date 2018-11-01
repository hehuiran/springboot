package com.yz.springboot.handler;

import com.yz.springboot.entity.BaseResponse;
import com.yz.springboot.exception.CommonException;
import com.yz.springboot.exception.ErrorEnum;
import com.yz.springboot.util.ResponseUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author huiRan
 * @date 2018/9/4
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CommonException.class)//声明需要捕获的异常类 - CommonException，就是只会捕获ErrorException异常了
    @ResponseBody
    public BaseResponse handle(Exception e) {
        if (e instanceof CommonException) {
            CommonException exception = (CommonException) e;
            return ResponseUtils.error(exception.getCode(), exception.getMsg());
        } else {
            return ResponseUtils.error(ErrorEnum.UNKNOWN_ERROR);
        }
    }
}
