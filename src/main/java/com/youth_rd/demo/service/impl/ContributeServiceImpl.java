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

import java.text.SimpleDateFormat;
import java.util.*;

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
        news.setTime(new Date());
        news.setIsDelete(0);
        news.setTag(0);
        return newsMapper.insert(news);
    }

    @Override
    public int reContribute(Integer id, String title, String img, Integer userId, Integer classId, String content,News oldNews) {
        oldNews.setTitle(title);
        oldNews.setClassId(classId);
        oldNews.setContent(content);
        oldNews.setImage(img);
        oldNews.setTime(new Date());
        oldNews.setIsDelete(0);
        oldNews.setTag(0);
        return newsMapper.updateAllById(oldNews);
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
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("number",newsList.size());
        resultList.add(map1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newsList = PageTool.getDateByCL(newsList,curr,limit);
        for(News n:newsList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",n.getId());
            map.put("title",n.getTitle());
            map.put("browse",n.getBrowse());
            map.put("comments",n.getComments());
            map.put("date",formatter.format(n.getTime()));
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
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("number",newsList.size());
        resultList.add(map1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newsList = PageTool.getDateByCL(newsList,curr,limit);
        for(News n:newsList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",n.getId());
            map.put("title",n.getTitle());
            map.put("date",formatter.format(n.getTime()));
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
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("number",newsList.size());
        resultList.add(map1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newsList = PageTool.getDateByCL(newsList,curr,limit);
        for(News n:newsList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",n.getId());
            map.put("title",n.getTitle());
            map.put("content",n.getAuditContent());
            map.put("pullDate",formatter.format(n.getTime()));
            resultList.add(map);
        }
        return resultList;
    }
}
