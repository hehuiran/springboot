package com.yz.springboot.handler;

import com.yz.springboot.constant.Constant;
import com.yz.springboot.entity.TUser;
import com.yz.springboot.exception.CommonException;
import com.yz.springboot.exception.ErrorEnum;
import com.yz.springboot.retention.AuthToken;
import com.yz.springboot.service.IUserService;
import com.yz.springboot.util.AuthUtils;
import com.yz.springboot.util.FormatUtils;
import com.yz.springboot.util.TextUtils;
import com.yz.springboot.util.TimeUtils;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author huiRan
 * @date 2018/9/17
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService service;
    @Autowired
    private RedisTemplate redisTemplate;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    /**
     * 在业务处理器处理请求之前被调用
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录
        AuthToken methodAnnotation = method.getAnnotation(AuthToken.class);

        // 有 @AuthToken 注解，需要认证
        if (methodAnnotation != null) {
            // 判断是否存在令牌信息，如果存在，则允许登录
            String accessToken = request.getHeader(Constant.AUTHORIZATION);

            if (TextUtils.isEmpty(accessToken)) {
                throw new CommonException(ErrorEnum.TOKEN_IS_NULL_ERROR);
            } else {
                Claims claims;
                try {
                    claims = AuthUtils.parseToken(accessToken);
                } catch (ExpiredJwtException e) {
                    throw new CommonException(ErrorEnum.TOKEN_FAILURE_ERROR);
                } catch (SignatureException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
                    throw new CommonException(ErrorEnum.TOKEN_ERROR);
                }

                String uid = claims.getId();
                TUser user = service.queryByUid(FormatUtils.parseLong(uid));
                if (user == null) {
                    throw new CommonException(ErrorEnum.USER_NOT_EXIST_ERROR);
                }
                //当前登录用户@InjectUser
                request.setAttribute(Constant.CURRENT_USER, user);

                //过期时间
                long expirationMillis = TimeUtils.date2Millis(claims.getExpiration());
                //签发时间
                long issuedAtMillis = TimeUtils.date2Millis(claims.getIssuedAt());
                //当前时间
                long currentMillis = System.currentTimeMillis();
                //使用的时间
                long usedMillis = currentMillis - issuedAtMillis;

                //若距离过期时间只剩下一半,则生成新的token返回给客户端
                int half = 2;
                if (usedMillis > (expirationMillis - issuedAtMillis) / half) {
                    //并发情况下保证只生成一次token
                    ValueOperations valueOperations = redisTemplate.opsForValue();
                    if (valueOperations.get(accessToken) == null) {
                        logger.debug("---------------generate token------------------");
                        String token = AuthUtils.createToken(uid);
                        response.setHeader(Constant.AUTHORIZATION, token);
                        valueOperations.set(accessToken, token, expirationMillis - currentMillis, TimeUnit.MILLISECONDS);
                    }
                }
                return true;
            }

        } else {//不需要登录可请求
            return true;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        logger.debug("---------------postHandle------------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.debug("---------------afterCompletion------------------");
    }
}
