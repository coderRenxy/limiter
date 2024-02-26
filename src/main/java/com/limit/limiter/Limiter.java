package com.limit.limiter;

import com.limit.limiter.DTO.LimiterDTO;

/**
 * 顶层接口
 * CreateTime: 2024-02-08 08:50
 */
public interface Limiter {

    /**
     * 添加
     * @param key
     * @param value
     * @param time 秒
     */
    void set(String key, Integer value, long time);

    /**
     * 获取,可能为空
     * @param key
     * @return
     */
    Integer get(String key);

    /**
     * 删除
     * @param key
     */
    void remove(String key);

    /**
     * 自增
     * @param key
     */
    void incr(String key, long time);

    /**
     * 检查是否达到限制,子类必须强制实现该接口
     * @param limiterDTO
     * @return
     */
    boolean check(LimiterDTO limiterDTO);

}
