package com.zhuhuix.startup.utils;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author zhuhuix
 * @date 2020-06-15
 * @date 2020-06-17 增加队列操作方法
 * @date 2020-06-22 增加集合(set)操作方法
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
     * 入队
     *
     * @param key   队列键值
     * @param value 元素
     * @return 添加数量
     */
    public long leftPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 向队列头部添加全部集合元素
     *
     * @param key  队列键值
     * @param list 集合
     * @return 返回添加的数量
     */
    public long leftPushAll(String key, List<Object> list) {
        return redisTemplate.opsForList().leftPushAll(key, list);
    }

    /**
     * 统计队列中所有元素数量
     *
     * @param key 队列键值
     * @return 队列中元素数量
     */
    public long size(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 返回队列中从起始位置到结束位置的集合元素
     *
     * @param key   队列键值
     * @param start 起始位置
     * @param end   结束位置
     * @return 返回集合
     */
    public List<Object> range(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 出队
     *
     * @param key 队列键值
     * @return 元素
     */
    public Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 弹出队列最新元素
     *
     * @param key 队列键值
     * @return 元素
     */
    public Object leftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 删除队列所有元素
     *
     * @param key 队列键值
     */
    public void deleteAll(String key) {
        redisTemplate.opsForList().trim(key, 0, 0);
        redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 向集合中增加元素
     *
     * @param key   集合键值
     * @param value 元素
     * @return 添加数量
     */
    public long setAdd(String key, Object value) {
        return redisTemplate.opsForSet().add(key,value);
    }

    /**
     * 向集合中批量增加元素
     *
     * @param key  集合键值
     * @param list 元素列表
     * @return 添加数量
     */
    public long setAdd(String key, List<Object> list) {
        return redisTemplate.opsForSet().add(key,list);
    }

    /**
     * 集合删除指定元素
     *
     * @param key   集合键值
     * @param value 指定元素
     * @return 删除数量
     */
    public long setRemove(String key, Object value) {
        return redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 集合批量删除指定元素
     *
     * @param key  集合键值
     * @param list 指定元素列表
     * @return 删除数量
     */
    public long setRemove(String key, List<Object> list) {
        return redisTemplate.opsForSet().remove(key, list);
    }

    /**
     * 取出两信集合的交集
     *
     * @param key1 集合1键值
     * @param key2 集合2键值
     * @return 交集
     */
    public Set<Object> setInter(String key1, String key2) {
        return redisTemplate.opsForSet().intersect(key1, key2);
    }

    /**
     * 取出多个集合的交集
     *
     * @param keys 键值列表
     * @return 交集
     */
    public Set<Object> setInter(List<Object> keys) {
        return redisTemplate.opsForSet().intersect(keys);
    }

    /**
     * 取出两个集合的差集
     *
     * @param key1 集合1键值
     * @param key2 集合2键值
     * @return 差集
     */
    public Set<Object> setDifference(String key1,String key2){
        return redisTemplate.opsForSet().difference(key1,key2);
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
