package com.limit.limiter.impl;

import com.limit.limiter.DTO.LimiterDTO;
import com.limit.limiter.LimiterAbstract;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CreateTime: 2024-02-20 18:18
 * 按照每X时间计数器
 */
public class CounterLimiter extends LimiterAbstract {

    RedisTemplate redisTemplate;

    public CounterLimiter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean check(LimiterDTO limiterDTO) {
        int limit = limiterDTO.limit;
        return check(limit);
    }

    static final String KEY= "COUNTER_LIMITER:";

    /**
     * 默认1分钟1个时间段
     * @param limit
     * @return
     */
    public boolean check(int limit){
        // 清除之前的时间段
        // 获取当前时间分钟级别
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String text = format.format(new Date());
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        try {
            // 转毫秒
            long waitClearTime = format.parse(text).getTime();
            // 删除
            zSetOperations.removeRangeByScore(KEY, 0, waitClearTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 查询当前时间内是否达到阈值
        Long count = redisTemplate.opsForZSet().zCard(KEY);
        if (count < limit) {
            long time1 = new Date().getTime();
            zSetOperations.add(KEY, String.valueOf(time1),time1);
            return false;
        } else {
            return true;
        }
    }
}
