package com.youth_rd.demo.service.impl;

import com.youth_rd.demo.dao.HistoryMapper;
import com.youth_rd.demo.service.BrowseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class BrowseServiceImpl implements BrowseService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    HistoryMapper historyMapper;


    @Override
    public void updateBrowseData(String message) {
        if(redisTemplate.hasKey(message)){
            ValueOperations<String,Map<String,Integer>> operations = redisTemplate.opsForValue();
            Map<String,Integer> map = operations.get(message);
            if(map.get("userId")==null){
                historyMapper.insert(1,map.get("newsId"),new Date());
            }else{
                historyMapper.insert(map.get("userId"),map.get("newsId"),new Date());
            }
            return ;
        }
    }
}
