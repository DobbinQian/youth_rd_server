package com.youth_rd.demo.service.impl;

import com.youth_rd.demo.dao.NewsMapper;
import com.youth_rd.demo.domain.News;
import com.youth_rd.demo.service.NewsService;
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
public class NewsServiceImpl implements NewsService {
    @Autowired
    NewsMapper newsMapper;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean newsIsExistById(Integer id) {
        News news = newsMapper.selectById(id);
        if(news==null){
            return false;
        }
        return true;
    }

    @Override
    public List<Map<String, Object>> getNewsByUserFollow(Integer id,Integer curr,Integer limit) {
        String key = "FollowNews_"+id;
        List<News> newsList;
        if(RedisTool.hasKey(redisTemplate,key)){
            newsList = RedisTool.getList(redisTemplate,key);
        }else{
            newsList = newsMapper.selectByFansId(id);
            RedisTool.setList(redisTemplate,key,newsList);
        }
        newsList = PageTool.getDateByCL(newsList,curr,limit);
        List<Map<String,Object>> resultList = new ArrayList<>();
        for(News n:newsList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",n.getId());
            map.put("title",n.getTitle());
            map.put("img",n.getImage());
            map.put("authorId",n.getAuthorId());
            map.put("authorName",n.getAuthor().getName());
            map.put("date",n.getTime());
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> getTopNews() {
        String key = "TopNews";
        List<News> newsList;
        if(RedisTool.hasKey(redisTemplate,key)){
            newsList = RedisTool.getList(redisTemplate,key);
        }else{
            newsList = newsMapper.selectByTop();
            RedisTool.setList(redisTemplate,key,newsList);
        }
        List<Map<String,Object>> resultList = new ArrayList<>();
        for(News n:newsList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",n.getId());
            map.put("title",n.getTitle());
            map.put("img",n.getImage());
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> getNewsList(Integer curr, Integer limit) {
        String key = "NewsList";
        List<News> newsList;
        if(RedisTool.hasKey(redisTemplate,key)){
            newsList = RedisTool.getList(redisTemplate,key);
        }else{
            newsList = newsMapper.selectAll();
            RedisTool.setList(redisTemplate,key,newsList);
        }
        newsList = PageTool.getDateByCL(newsList,curr,limit);
        List<Map<String,Object>> resultList = new ArrayList<>();
        if(newsList == null){
            return resultList;
        }
        for(News n:newsList){
            Map<String,Object> map = new HashMap<>();
            map.put("title",n.getTitle());
            map.put("img",n.getImage());
            map.put("date",n.getTime());
            map.put("id",n.getId());
            map.put("authorId",n.getAuthorId());
            map.put("authorName",n.getAuthor().getName());
            map.put("browse",n.getBrowse());
            map.put("comments",n.getComments());
            map.put("classId",n.getaClass().getId());
            map.put("className",n.getaClass().getName());
            map.put("plateId",n.getPlate().getId());
            map.put("plateName",n.getPlate().getName());
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> getNewsListByPlateId(Integer id,Integer curr,Integer limit) {
        String key = "NewsListByPlateId_"+id;
        List<News> newsList;
        if(RedisTool.hasKey(redisTemplate,key)){
            newsList = RedisTool.getList(redisTemplate,key);
        }else {
            newsList = newsMapper.selectByPlateId(id);
            RedisTool.setList(redisTemplate,key,newsList);
        }
        newsList = PageTool.getDateByCL(newsList,curr,limit);
        List<Map<String,Object>> resultList = new ArrayList<>();
        if(newsList == null){
            return resultList;
        }
        for(News n:newsList){
            Map<String,Object> map = new HashMap<>();
            map.put("title",n.getTitle());
            map.put("img",n.getImage());
            map.put("date",n.getTime());
            map.put("id",n.getId());
            map.put("authorId",n.getAuthorId());
            map.put("authorName",n.getAuthor().getName());
            map.put("browse",n.getBrowse());
            map.put("comments",n.getComments());
            map.put("classId",n.getaClass().getId());
            map.put("className",n.getaClass().getName());
            map.put("plateId",n.getPlate().getId());
            map.put("plateName",n.getPlate().getName());
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> getNewsListByClassId(Integer id,Integer curr,Integer limit) {
        String key = "NewsListByClassId_"+id;
        List<News> newsList;
        if(RedisTool.hasKey(redisTemplate,key)){
            newsList = RedisTool.getList(redisTemplate,key);
        }else {
            newsList = newsMapper.selectByClassId(id);
            RedisTool.setList(redisTemplate,key,newsList);
        }
        newsList = PageTool.getDateByCL(newsList,curr,limit);
        List<Map<String,Object>> resultList = new ArrayList<>();
        if(newsList == null){
            return resultList;
        }
        for(News n:newsList){
            Map<String,Object> map = new HashMap<>();
            map.put("title",n.getTitle());
            map.put("img",n.getImage());
            map.put("date",n.getTime());
            map.put("id",n.getId());
            map.put("authorId",n.getAuthorId());
            map.put("authorName",n.getAuthor().getName());
            map.put("browse",n.getBrowse());
            map.put("comments",n.getComments());
            map.put("classId",n.getaClass().getId());
            map.put("className",n.getaClass().getName());
            map.put("plateId",n.getPlate().getId());
            map.put("plateName",n.getPlate().getName());
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public Map<String, Object> getNewsContentById(Integer id) {
        //TODO 做缓存
        News news = newsMapper.selectById(id);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("id",news.getId());
        resultMap.put("title",news.getTitle());
        resultMap.put("authorId",news.getAuthorId());
        resultMap.put("authorName",news.getAuthor().getName());
        resultMap.put("date",news.getTime());
        resultMap.put("browse",news.getBrowse());
        resultMap.put("comments",news.getComments());
        resultMap.put("content",news.getContent());
        return resultMap;
    }

    @Override
    public Map<String, Object> getRankList() {
//        String key = "RankList";
//        Map<String,Object> resultMap;
//        ValueOperations<String, Map<String,Object>> operations = redisTemplate.opsForValue();
//        if(redisTemplate.hasKey(key)){
//            resultMap = operations.get(key);
//            return resultMap;
//        }
//
//        resultMap = new HashMap<>();
//        Map<String,Object> monthMap = new HashMap<>();
//        List<News>

        return null;
    }

    @Override
    public List<Map<String, Object>> getRecommendListById(Integer id) {
        return null;
    }
}
