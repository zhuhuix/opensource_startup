package com.zhuhuix.startup.testredis;

import com.zhuhuix.startup.utils.RedisUtils;
import com.zhuhuix.startup.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;
import java.util.Set;

/**
 * Redis测试
 *
 * @author zhuhuix
 * @date 2020-06-22 set测试
 */
@SpringBootTest
@Slf4j
class TestSet {
    @Test
    void test() {
        RedisUtils redisUtils = SpringContextHolder.getBean(RedisUtils.class);
        //获取交集：相同客户
        Set<Object> setInter=redisUtils.setInter("openId1_customer","openId2_customer");
        Iterator iterator = setInter.iterator();
        log.info("openId1_customer与openId2_customer相同的客户为：");
        while(iterator.hasNext()){
            log.info(iterator.next().toString());
        }
        //获取差集：不同客户
        Set<Object> setDiff=redisUtils.setDifference("openId1_customer","openId2_customer");
        iterator = setDiff.iterator();
        log.info("openId1_customer与openId2_customer不同的客户为：");
        while(iterator.hasNext()){
            log.warn(iterator.next().toString());
        }
        //获取差集：不同客户
        Set<Object> setDiff1=redisUtils.setDifference("openId2_customer","openId1_customer");
        iterator = setDiff1.iterator();
        log.info("openId2_customer与openId1_customer不同的客户为：");
        while(iterator.hasNext()){
            log.warn(iterator.next().toString());
        }
    }
}
