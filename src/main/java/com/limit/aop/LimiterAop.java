package com.limit.aop;


import com.limit.exception.InterceptException;
import com.limit.limiter.DTO.LimiterDTO;
import com.limit.limiter.Limit;
import com.limit.limiter.handler.LimiterHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * CreateTime: 2024-02-08 09:40
 */
@Aspect
public class LimiterAop {

    @Autowired
    HttpServletRequest request;

    @Autowired
    LimiterHandler restrictHandler;
    @Pointcut("@annotation(com.limit.limiter.Limit)")
    public void aopPoint() {
    }


    /**
     * 拦截Limit注解的请求
     * @param joinPoint
     * @param limiter
     * @return
     * @throws Throwable
     */
    @Around("aopPoint() && @annotation(limiter)")
    public Object restriction(ProceedingJoinPoint joinPoint, Limit limiter) throws Throwable {
        String key = request.getRequestURI();
        int limit = limiter.limit();
        int time = limiter.time();
        LimiterDTO limiterDTO = new LimiterDTO(limit,time,key);
        boolean result = restrictHandler.check(limiterDTO);
        if (result){
            throw new InterceptException(limiter.msg());
        }
        Object o = joinPoint.proceed();
        return o;
    }


}
