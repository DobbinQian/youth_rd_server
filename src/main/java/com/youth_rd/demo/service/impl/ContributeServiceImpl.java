package com.youth_rd.demo.service.impl;

import com.youth_rd.demo.dao.NewsMapper;
import com.youth_rd.demo.domain.News;
import com.youth_rd.demo.domain.User;
import com.youth_rd.demo.service.ContributeService;
import com.youth_rd.demo.tools.PageTool;
import com.youth_rd.demo.tools.RedisTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContributeServiceImpl implements ContributeService {
    @Autowired
    NewsMapper newsMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public int contribute(String title,String img,Integer classId, String content, User author) {
        News news = new News();
        news.setTitle(title);
        news.setClassId(classId);
        news.setContent(content);
        news.setAuthorId(author.getId());
        news.setImage(img);
        news.setIsDelete(0);
        news.setTag(0);
        return newsMapper.insert(news);
    }

    @Override
    public List<Map<String, Object>> getContributeList(Integer id,Integer curr,Integer limit) {
        String key = "CL"+id;
        List<News> newsList;
        if(RedisTool.hasKey(redisTemplate,key)){
            newsList = RedisTool.getList(redisTemplate,key);
        }else{
            newsList = newsMapper.selectPassByUserId(id);
            RedisTool.setList(redisTemplate,key,newsList);
        }
        newsList = PageTool.getDateByCL(newsList,curr,limit);
        List<Map<String,Object>> resultList = new ArrayList<>();
        for(News n:newsList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",n.getId());
            map.put("title",n.getTitle());
            map.put("browse",n.getBrowse());
            map.put("comments",n.getComments());
            map.put("date",n.getTime());
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> getAuditList(Integer id,Integer curr,Integer limit) {
        String key = "AL"+id;
        List<News> newsList;
        if(RedisTool.hasKey(redisTemplate,key)){
            newsList = RedisTool.getList(redisTemplate,key);
        }else{
            newsList = newsMapper.selectAuditByUserId(id);
            RedisTool.setList(redisTemplate,key,newsList);
        }
        newsList = PageTool.getDateByCL(newsList,curr,limit);
        List<Map<String,Object>> resultList = new ArrayList<>();
        for(News n:newsList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",n.getId());
            map.put("title",n.getTitle());
            map.put("date",n.getTime());
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> getReturnList(Integer id,Integer curr,Integer limit) {
        String key = "RL"+id;
        List<News> newsList;
        if(RedisTool.hasKey(redisTemplate,key)){
            newsList = RedisTool.getList(redisTemplate,key);
        }else{
            newsList = newsMapper.selectReturnByUserId(id);
            RedisTool.setList(redisTemplate,key,newsList);
        }
        newsList = PageTool.getDateByCL(newsList,curr,limit);
        List<Map<String,Object>> resultList = new ArrayList<>();
        for(News n:newsList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",n.getId());
            map.put("title",n.getTitle());
            map.put("content",n.getTime());
            map.put("pullDate",n.getTime());
            resultList.add(map);
        }
        return resultList;
    }
}
