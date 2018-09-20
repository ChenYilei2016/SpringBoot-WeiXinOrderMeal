package com.chenyilei.controller;

import com.chenyilei.redislock.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chenyilei
 * @date 2018/9/20/0020-18:30
 * hello everyone
 */
@Controller
@Slf4j
public class TestRedisLockController {
    @Autowired
    RedisLock redisLock;

    private static int sum1= 100000;
    private static int sum2= 0;
    @RequestMapping("/testRedis/{id}")
    public void testR(@PathVariable("id")String id){
        String outTime = String.valueOf(System.currentTimeMillis()+1500);
        if(redisLock.lockProduct(id,outTime)){
            return ;
        }

        sum1 = sum1 - 1;
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sum2 = sum2 + 1;
        log.info("已经买:"+sum2 +"还剩:"+sum1);

        redisLock.unlockProduct(id,outTime);
    }

    @RequestMapping("/testQ")
    public void testQ(@PathVariable("id")String id){
        sum1 = 100000;
        sum2=0;
    }
}
