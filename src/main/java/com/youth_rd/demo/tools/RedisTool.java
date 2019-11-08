package com.youth_rd.demo.tools;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RedisTool<T> {

    public static boolean hasKey(RedisTemplate redisTemplate,String key){
        return redisTemplate.hasKey(key);
    }

    public static <T> List<T> getList(RedisTemplate redisTemplate,String key){
        ValueOperations<String, List<T>> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    public static <T> void setList(RedisTemplate redisTemplate,String key, List<T> date){
        ValueOperations<String, List<T>> operations = redisTemplate.opsForValue();
        operations.set(key,date,10,TimeUnit.SECONDS);
    }
}
