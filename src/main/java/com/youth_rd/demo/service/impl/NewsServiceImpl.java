package com.youth_rd.demo.service.impl;

import com.youth_rd.demo.dao.NewsMapper;
import com.youth_rd.demo.domain.News;
import com.youth_rd.demo.service.NewsService;
import com.youth_rd.demo.tools.PageTool;
import com.youth_rd.demo.tools.RedisTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    NewsMapper newsMapper;
    @Autowired
    RedisTemplate redisTemplate;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
        if(newsList==null){
            return null;
        }
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("number",newsList.size());
        resultList.add(map1);
        for(News n:newsList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",n.getId());
            map.put("title",n.getTitle());
            map.put("classId",n.getClassId());
            map.put("plateId",n.getPlate().getId());
            map.put("browse",n.getBrowse());
            map.put("comments",n.getComments());
            map.put("content",n.getContent());
            map.put("authorImg",n.getAuthor().getImage());
            map.put("img",n.getImage());
            map.put("authorId",n.getAuthorId());
            map.put("authorName",n.getAuthor().getName());
            map.put("date",formatter.format(n.getTime()));
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
            map.put("date",formatter.format(n.getTime()));
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
            map.put("date",formatter.format(n.getTime()));
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
            map.put("date",formatter.format(n.getTime()));
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
        List<News> newsList = newsMapper.selectAllByIPCTTU(id,null,null,null,null,null);
        if(newsList.size()==0){
            return null;
        }
        News news = newsList.get(0);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("id",news.getId());
        resultMap.put("title",news.getTitle());
        resultMap.put("classId",news.getClassId());
        resultMap.put("plateId",news.getPlate().getId());
        resultMap.put("authorId",news.getAuthorId());
        resultMap.put("authorName",news.getAuthor().getName());
        resultMap.put("date",formatter.format(news.getTime()));
        resultMap.put("browse",news.getBrowse());
        resultMap.put("comments",news.getComments());
        resultMap.put("content",news.getContent());
        return resultMap;
    }

    @Override
    public Map<String, Object> getRankList() {
        String key = "RankList";
        Map<String,Object> resultMap;
        ValueOperations<String, Map<String,Object>> operations = redisTemplate.opsForValue();
        if(redisTemplate.hasKey(key)){
            resultMap = operations.get(key);
            return resultMap;
        }
        resultMap = new HashMap<>();
        Map<String,Object> dayMap = new HashMap<>();
        List<Map<String,Object>> list = new ArrayList<>();
        List<News> newsListDB = newsMapper.selectByDayBrowse();
        for(News n:newsListDB){
            Map<String,Object> map = new HashMap<>();
            map.put("id",n.getId());
            map.put("title",n.getTitle());
            map.put("number",n.getBrowse());
            list.add(map);
        }
        dayMap.put("browse",new ArrayList<>(list));
        List<News> newsListDC = newsMapper.selectByDayComments();
        list.clear();
        for(News n:newsListDC){
            Map<String,Object> map = new HashMap<>();
            map.put("id",n.getId());
            map.put("title",n.getTitle());
            map.put("number",n.getComments());
            list.add(map);
        }
        dayMap.put("comments",new ArrayList<>(list));
        resultMap.put("day",new HashMap<>(dayMap));
        dayMap.clear();
        List<News> newsListWB = newsMapper.selectByWeekBrowse();
        list.clear();
        for(News n:newsListWB){
            Map<String,Object> map = new HashMap<>();
            map.put("id",n.getId());
            map.put("title",n.getTitle());
            map.put("number",n.getBrowse());
            list.add(map);
        }
        dayMap.put("browse",new ArrayList<>(list));
        List<News> newsListWC = newsMapper.selectByWeekComments();
        list.clear();
        for(News n:newsListWC){
            Map<String,Object> map = new HashMap<>();
            map.put("id",n.getId());
            map.put("title",n.getTitle());
            map.put("number",n.getComments());
            list.add(map);
        }
        dayMap.put("comments",new ArrayList<>(list));
        resultMap.put("week",new HashMap<>(dayMap));
        dayMap.clear();
        List<News> newsListMB = newsMapper.selectByMonthBrows();
        list.clear();
        for(News n:newsListMB){
            Map<String,Object> map = new HashMap<>();
            map.put("id",n.getId());
            map.put("title",n.getTitle());
            map.put("number",n.getBrowse());
            list.add(map);
        }
        dayMap.put("browse",new ArrayList<>(list));
        List<News> newsListMC = newsMapper.selectByMonthComments();
        list.clear();
        for(News n:newsListMC){
            Map<String,Object> map = new HashMap<>();
            map.put("id",n.getId());
            map.put("title",n.getTitle());
            map.put("number",n.getComments());
            list.add(map);
        }
        dayMap.put("comments",new ArrayList<>(list));
        resultMap.put("month",new HashMap<>(dayMap));

        operations.set(key,resultMap,10, TimeUnit.MINUTES);
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> getRecommendListById(Integer id) {

        return getNewsList(1,10);
    }

    @Override
    public Map<String, Object> getReturnContentById(Integer userId, Integer newsId) {
        News news = newsMapper.selectReturnContentById(userId,newsId);
        if(news==null){
            return null;
        }else{
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("id",news.getId());
            resultMap.put("title",news.getTitle());
            resultMap.put("img",news.getImage());
            resultMap.put("classId",news.getClassId());
            resultMap.put("plateId",news.getPlate().getId());
            resultMap.put("authorId",news.getAuthorId());
            resultMap.put("authorName",news.getAuthor().getName());
            resultMap.put("date",formatter.format(news.getTime()));
            resultMap.put("content",news.getContent());
            return resultMap;
        }
    }

    @Override
    public List<Map<String, Object>> getNewsByTC(Integer op, String value,Integer curr,Integer limit) {
        List<News> newsList = newsMapper.selectByTC(op,value);
        newsList = PageTool.getDateByCL(newsList,curr,limit);
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("number",newsList.size());
        resultList.add(map1);
        if(newsList == null){
            return resultList;
        }
        for(News n:newsList){
            Map<String,Object> map = new HashMap<>();
            map.put("title",n.getTitle());
            map.put("img",n.getImage());
            map.put("date",formatter.format(n.getTime()));
            map.put("id",n.getId());
            map.put("authorId",n.getAuthorId());
            map.put("authorName",n.getAuthor().getName());
            map.put("browse",n.getBrowse());
            map.put("comments",n.getComments());
            map.put("content",n.getContent());
            map.put("classId",n.getaClass().getId());
            map.put("plateId",n.getPlate().getId());
            resultList.add(map);
        }
        return resultList;
    }
}
