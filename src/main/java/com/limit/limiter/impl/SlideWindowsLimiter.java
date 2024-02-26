package com.limit.limiter.impl;

import com.limit.limiter.DTO.LimiterDTO;
import com.limit.limiter.LimiterAbstract;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.concurrent.TimeUnit;

/**
 * CreateTime: 2024-02-20 23:18
 * 滑动窗口
 */
public class SlideWindowsLimiter extends LimiterAbstract {


    RedisTemplate redisTemplate;

    public SlideWindowsLimiter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    // 具体的逻辑
    @Override
    public boolean check(LimiterDTO restrictDTO) {
        return canAccess(10,5);
    }

    static final String SLIDING_WINDOW= "SLIDING_WINDOW:";
    /**
     * 判断key的value中的有效访问次数是否超过最大限定值maxCount，若没超过，
     * 调用increment方法，将窗口内的访问数加一
     * 判断与数量增长同步处理
     *
     * @param windowInSecond 窗口间隔，秒
     * @param maxCount       最大计数
     * @return 可访问 or 不可访问
     */
    public boolean canAccess(int windowInSecond, long maxCount) {
        String key = SLIDING_WINDOW;
        //按key统计集合中的有效数量
        Long count = redisTemplate.opsForZSet().zCard(key);
        if (count < maxCount) {
            increment(key, windowInSecond);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 滑动窗口计数增长
     *
     * @param key            redis key
     * @param windowInSecond 窗口间隔，秒
     */
    public void increment(String key, Integer windowInSecond) {
        // 当前时间
        long currentMs = System.currentTimeMillis();
        // 废弃窗口开始时间,用于删除废弃窗口
        long windowStartMs = currentMs - windowInSecond * 1000;
        // 单例模式(提升性能)
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        // 清除窗口过期成员
        zSetOperations.removeRangeByScore(key, 0, windowStartMs);
        // 添加当前时间 value=当前时间戳 score=当前时间戳
        zSetOperations.add(key, String.valueOf(currentMs), currentMs);
        // 设置key过期时间
        redisTemplate.expire(key, windowInSecond, TimeUnit.SECONDS);
    }

}
