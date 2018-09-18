package com.chenyilei.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author chenyilei
 * @date 2018/9/16/0016-15:28
 * hello everyone
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    @Autowired
    SellerInfoRepository sellerInfoRepository;
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void findByUsernameAndPassword() {
//        redisTemplate.opsForValue().set("a","bc");
    }
}