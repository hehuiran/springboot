package com.yz.springboot.handler;

import com.yz.springboot.constant.Constant;
import com.yz.springboot.entity.TUser;
import com.yz.springboot.exception.CommonException;
import com.yz.springboot.exception.ErrorEnum;
import com.yz.springboot.retention.InjectUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author huiRan
 * @date 2018/9/17
 */
public class InjectUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 用于判定是否需要处理该参数分解，返回true为需要，并会去调用下面的方法resolveArgument.
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //判断是否能转成UserBase 类型
        return parameter.getParameterType().isAssignableFrom(TUser.class)
                //是否有CurrentUser注解
                && parameter.hasParameterAnnotation(InjectUser.class);
    }

    /**
     * 真正用于处理参数分解的方法，返回的Object就是controller方法上的形参对象.
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        TUser user = (TUser) webRequest.getAttribute(Constant.CURRENT_USER, RequestAttributes.SCOPE_REQUEST);
        if (user != null) {
            return user;
        }
        throw new CommonException(ErrorEnum.LOSE_USER_ERROR);
    }
}
