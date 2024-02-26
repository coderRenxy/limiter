package com.limit.config;

import com.limit.aop.LimiterAop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CreateTime: 2024-02-13 14:56
 * 设置LimiterHandler中设置具体限流实现类
 */
@Configuration
public class LimiterAutoConfig {

    @Bean
    public LimiterAop limiterAop(){
        return new LimiterAop();
    }

}
