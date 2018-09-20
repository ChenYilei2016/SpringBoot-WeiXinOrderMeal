package com.chenyilei.redislock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sun.awt.Mutex;

/**
 * @author chenyilei
 * @date 2018/9/20/0020-17:46
 * hello everyone
 */

@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static Mutex mutex = new Mutex();

    /**
     *
     * @param key
     * @param value 超时的时间戳
     * @return
     */
    public boolean lockProduct(String key,String value){
        //没人使用, 可以加锁
        if( redisTemplate.opsForValue().setIfAbsent(key,value) ){
            return true;
        }
        //有无超时
        String currentValue = redisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(currentValue)&&
            Long.parseLong(currentValue)<System.currentTimeMillis()  ){
            //mutex.lock();
            String oldValue= redisTemplate.opsForValue().getAndSet(key,value);
            if( !StringUtils.isEmpty(oldValue) && currentValue.equals( oldValue ) ){
                //mutex.unlock();
                return true;
            }
            //mutex.unlock();
        }
       return false;
    }

    public void unlockProduct(String key,String value){
        try {
            String temp = redisTemplate.opsForValue().get(key);
            if(!StringUtils.isEmpty(temp)&& value.equals(temp)){
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e){
            log.error("[redis锁]解锁异常,{}",e);
        }
    }
}
