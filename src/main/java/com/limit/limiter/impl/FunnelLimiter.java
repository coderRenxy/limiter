package com.limit.limiter.impl;

import com.limit.limiter.DTO.LimiterDTO;
import com.limit.limiter.LimiterAbstract;

/**
 * CreateTime: 2024-02-20 20:27
 * 漏斗限流
 * 以一定的速率进行限流
 * 实现方案:
 * 开一个线程每秒放多少请求进来
 */
public class FunnelLimiter extends LimiterAbstract {


    @Override
    public boolean check(LimiterDTO limiterDTO) {
        return allow();
    }


    static class Funnel {

        // 水流速度
        private float waterRate;

        // 漏斗容量
        private Integer capacity;

        // 漏斗剩余容量
        private Integer leftCapacity;

        // 上一次访问时间戳
        private long lastAccess;

        private static Funnel funnel;
        /**
         *
         * @param waterRate 出水速率
         * @param capacity 初始容量
         */
        private Funnel(Integer waterRate, Integer capacity) {
            this.waterRate = waterRate;
            this.capacity = capacity;
            this.leftCapacity = capacity;
            this.lastAccess = System.currentTimeMillis();
        }
        public synchronized static Funnel getInstance(){
            if (funnel == null){
                funnel = new Funnel(1,5);
            }
            return funnel;
        }


        /**
         * 出水
         */
        public void runningWater() {
            long nowTime = System.currentTimeMillis();
            long deltaTime = nowTime - lastAccess;

            // 计算当前时间单位内的出水量
            int  deltaCapacity = (int) (waterRate * deltaTime);
            // 更正上一次请求时间
            this.lastAccess = nowTime;
            // 上一次请求时间过长故而计算出负值需要重新校验
            if (deltaCapacity < 0) {
                this.leftCapacity = capacity;
                return;
            }
            if (deltaCapacity < 1) {
                return;
            }
            // 单位时间内出水量 + 剩余容量 = 当前桶空间大小
            this.leftCapacity += deltaCapacity;

            if (this.leftCapacity > this.capacity) {
                this.leftCapacity = this.capacity;
            }
        }

        /**
         * 进水
         * @param waterCapacity
         * @return
         */
        public boolean watering(Integer waterCapacity) {
            // 先计算当前桶容量
            runningWater();
            if (leftCapacity >= waterCapacity) {
                this.leftCapacity -= waterCapacity;
                return false;
            }
            return true;
        }

    }

    public boolean allow() {
        return Funnel.getInstance().watering(1);
    }




}
