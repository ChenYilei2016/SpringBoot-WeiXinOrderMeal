package com.chenyilei.aspect;

import com.chenyilei.constant.RedisConstant;
import com.chenyilei.exception.SellerAuthorizeException;
import com.chenyilei.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenyilei
 * @date 2018/9/18/0018-15:30
 * hello everyone
 */
@Aspect
@Component
@Slf4j
public class SellerLoginAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(* com.chenyilei.controller.Sell*.*(..) ) && " +
             "!execution(* com.chenyilei.controller.SellerUserController.*(..) )")
    public void verify(){}

    //TODO: 测试 重定向
    @Before("verify()")
    public void doVerify() throws IOException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        Cookie cookie = CookieUtil.get(request, "token");
        if(null == cookie){
            log.info("无cookie 无登陆");
            throw new SellerAuthorizeException();
        }

        String tokenvalue= redisTemplate.opsForValue().get(RedisConstant.TOKEN_PREFIX+cookie.getValue());
        if(StringUtils.isEmpty(tokenvalue)){
            log.info("redis 无登陆");
            throw new SellerAuthorizeException();
        }

    }

}
