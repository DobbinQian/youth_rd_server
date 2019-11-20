package com.youth_rd.demo.tools;

import com.youth_rd.demo.domain.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.Map;
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

    public static void setUser(RedisTemplate redisTemplate,String key, User user){
        ValueOperations<String,User> operations = redisTemplate.opsForValue();
        operations.set(key,user,1,TimeUnit.HOURS);
    }

    public static User getUser(RedisTemplate redisTemplate,String key){
        ValueOperations<String,User> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    public static void setBrowseData(RedisTemplate redisTemplate,String key,Map<String,Integer> map){
        ValueOperations<String, Map<String,Integer>> operations = redisTemplate.opsForValue();
        operations.set(key,map,1,TimeUnit.DAYS);
    }
}
