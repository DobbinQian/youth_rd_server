package com.youth_rd.demo.service.impl;

import com.youth_rd.demo.dao.NewsMapper;
import com.youth_rd.demo.domain.News;
import com.youth_rd.demo.service.NewsManageService;
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
public class NewsManageServiceImpl implements NewsManageService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    NewsMapper newsMapper;
    @Override
    public List<Map<String, Object>> getAllAuditList(Integer curr, Integer limit) {
        String key = "AllAutitList";
        List<News> newsList;
        if(RedisTool.hasKey(redisTemplate,key)){
            newsList = RedisTool.getList(redisTemplate,key);
        }else{
            newsList = newsMapper.selectAllAuditList();
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
    public int audit(Integer id, Integer op, String content) {
        return newsMapper.updateTagById(id,op,content);
    }

    @Override
    public List<Map<String, Object>> admGetNewsList(Integer id, String title, Integer curr, Integer limit) {
        String key = "NewsList_"+id+"_"+title;
        List<News> newsList;
        if(RedisTool.hasKey(redisTemplate,key)){
            newsList = RedisTool.getList(redisTemplate,key);
        }else{
            newsList = newsMapper.selectByIdTitle(id,title);
            RedisTool.setList(redisTemplate,key,newsList);
        }
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
    public int sumOfTop() {
        return newsMapper.selectTopSum();
    }

    @Override
    public int editTopLine(List<Integer> obj) {
        return newsMapper.updateTopLineList(obj);
    }

    @Override
    public int setTopLine(Integer id, String topImg, Integer queue) {
        return newsMapper.updateTopLine(id, topImg, queue);
    }

    @Override
    public int deleteOrRecover(Integer id, Integer op) {
        return newsMapper.updateIsDelete(id, op==1?1:0);
    }

    @Override
    public List<Map<String, Object>> getTopLine() {
        List<News> newsList = newsMapper.selectTopLine();
        List<Map<String,Object>> resultList = new ArrayList<>();
        for(News n:newsList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",n.getId());
            map.put("title",n.getTitle());
            map.put("img",n.getTopImg());
            resultList.add(map);
        }
        return resultList;
    }
}
