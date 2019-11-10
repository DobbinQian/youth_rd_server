package com.youth_rd.demo.service.impl;

import com.youth_rd.demo.dao.InformationMapper;
import com.youth_rd.demo.domain.Information;
import com.youth_rd.demo.service.InformationService;
import com.youth_rd.demo.tools.RedisTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InformationServiceImpl implements InformationService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    InformationMapper informationMapper;
    @Override
    public List<Map<String, Object>> getFeedbackList(Integer curr, Integer limit) {
        String key = "FBList";
        List<Information> informationList;
        if(RedisTool.hasKey(redisTemplate,key)){
            informationList = RedisTool.getList(redisTemplate,key);
        }else{
            informationList = informationMapper.selectByAdm();
            RedisTool.setList(redisTemplate,key,informationList);
        }
        List<Map<String,Object>> resultList = new ArrayList<>();
        for(Information i:informationList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",i.getId());
            map.put("userId",i.getSendId());
            map.put("articleId",i.getContent().substring(0,5));
            map.put("content",i.getContent());
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public int feedbackByNewsId(Integer newsId, Integer userId, String content) {
        Information information = new Information();
        information.setSendId(userId);
        information.setReceiveId(1);
        information.setSendTime(new Date());
        information.setContent(newsId+content);
        return informationMapper.insert(information);
    }

    @Override
    public int replyFeedbackById(Integer userId, Integer admId, String content) {
        Information information = new Information();
        information.setSendId(1);
        information.setReceiveId(userId);
        information.setSendTime(new Date());
        information.setContent(content);
        return informationMapper.insert(information);
    }
}