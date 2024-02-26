package com.limit.limiter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解定义
 * CreateTime: 2024-02-05 00:37
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit {

    int limit() default 0;

    int time() default 0;

    String key() default "";

    String msg() default "系统服务繁忙";
}
