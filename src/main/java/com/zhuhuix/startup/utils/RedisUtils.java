package com.zhuhuix.startup.utils;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author zhuhuix
 * @date 2020-06-15
 */
@Component
@AllArgsConstructor
public class RedisUtils {
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * HashGet根据键值得到对象
     *
     * @param key  键值 @NotNull
     * @param item 项目 @NotNull
     * @return 对象
     */
    public Object hashGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 根据键值向hash表中写入对象
     *
     * @param key   键值 @NotNull
     * @param item  项目 @NotNull
     * @param value 对象 @NotNull
     * @return true 成功 false失败
     */
    public boolean hashSet(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 根据键值向hash表中写入对象，并设置过期时间
     *
     * @param key   键值 @NotNull
     * @param item  项目 @NotNull
     * @param value 对象 @NotNull
     * @param time  过期时间(秒) @NotNull
     * @return true 成功 false失败
     */
    public boolean hashSet(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据键值对某一项目的进行累加计数
     *
     * @param key 键值
     * @param l   累加数
     */
    public long increment(String key, long l) {
        return redisTemplate.opsForValue().increment(key, l);
    }

    /**
     * 根据键值对某一项目的进行累加计数,并设置过期时间
     *
     * @param key  键值
     * @param l    累加数
     * @param time 过期时间(秒)
     */
    public long increment(String key, long l, long time) {
        long count = redisTemplate.opsForValue().increment(key, l);
        if (time > 0) {
            expire(key, time);
        }
        return count;
    }

    /**
     * 指定缓存的失效时间
     *
     * @param key  键值 @NotNull
     * @param time 时间(秒) @NotNull
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
