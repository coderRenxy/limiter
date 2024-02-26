package com.limit.limiter.handler;

import com.limit.limiter.Limiter;
import com.limit.limiter.DTO.LimiterDTO;

/**
 * CreateTime: 2024-02-08 09:37
 * 限流处理器
 */
public class LimiterHandler implements Limiter {

    Limiter limiter;

    public LimiterHandler (){

    }

    public LimiterHandler(Limiter l){
        this.limiter =l;
    }

    @Override
    public void set(String key, Integer value, long time) {
        limiter.set(key,value,time);
    }

    @Override
    public Integer get(String key) {
        return limiter.get(key);
    }

    @Override
    public void remove(String key) {
        limiter.remove(key);
    }

    @Override
    public void incr(String key,long time) {
        limiter.incr(key,time);
    }

    @Override
    public boolean check(LimiterDTO restrictDTO) {
        return limiter.check(restrictDTO);
    }


}
