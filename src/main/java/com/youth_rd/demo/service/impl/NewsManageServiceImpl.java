package com.youth_rd.demo.service.impl;

import com.youth_rd.demo.dao.NewsMapper;
import com.youth_rd.demo.domain.News;
import com.youth_rd.demo.service.NewsManageService;
import com.youth_rd.demo.tools.PageTool;
import com.youth_rd.demo.tools.RedisTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("number",newsList.size());
        resultList.add(map1);
        newsList = PageTool.getDateByCL(newsList,curr,limit);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
    public int audit(Integer id, Integer op, String content) {
        return newsMapper.updateTagById(id,op,content);
    }

    @Override
    public List<Map<String, Object>> admGetNewsList(Integer id,Integer plateId,Integer classId, String title,Integer tag,String username, Integer curr, Integer limit) {
        String key = "NewsList_"+id+"_"+title;
        List<News> newsList;
//        if(RedisTool.hasKey(redisTemplate,key)){
//            newsList = RedisTool.getList(redisTemplate,key);
//        }else{
//            newsList = newsMapper.selectAllByIPCTTU(id,plateId,classId,tag,title,username);
//            RedisTool.setList(redisTemplate,key,newsList);
//        }
        newsList = newsMapper.selectAllByIPCTTU(id,plateId,classId,tag,title,username);
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("number",newsList.size());
        resultList.add(map1);
        newsList = PageTool.getDateByCL(newsList,curr,limit);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(newsList==null){
            return resultList;
        }
        for(News n:newsList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",n.getId());
            map.put("title",n.getTitle());
            map.put("browse",n.getBrowse());
            map.put("comments",n.getComments());
            map.put("date",formatter.format(n.getTime()));
            map.put("plateId",n.getPlate().getId());
            map.put("classId",n.getClassId());
            if(n.getTag()==0){
                map.put("tag","未审核");
                map.put("isTop",0);
            }else if(n.getTag()==1){
                map.put("tag","已投稿");
                map.put("isTop",0);
            }else if(n.getTag()==-1){
                map.put("tag","被退回");
                map.put("isTop",0);
            }else{
                map.put("tag","头条:"+(n.getTag()-1));
                map.put("isTop",1);
            }
            map.put("isDelete",n.getIsDelete());
            map.put("username",n.getAuthor().getName());
            map.put("userId",n.getAuthorId());
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
            map.put("img",n.getImage());
            resultList.add(map);
        }
        return resultList;
    }
}
