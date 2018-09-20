package com.chenyilei.service.impl;

import com.chenyilei.redislock.RedisLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author chenyilei
 * @date 2018/9/20/0020-18:27
 * hello everyone
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TESTRedisLock {

    @Autowired
    RedisLock redisLock;

    @Test
    public void t1(){

    }

}
