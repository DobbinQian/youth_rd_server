package com.youth_rd.demo.service.impl;

import com.youth_rd.demo.service.BrowseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class BrowseServiceImpl implements BrowseService {

    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public void updateBrowseData(String message) {
        System.out.println("一次浏览记录："+message);
    }
}
