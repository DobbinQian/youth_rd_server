package com.youth_rd.demo.service;

import java.util.List;
import java.util.Map;

public interface NewsService {
    //通过Id判断文章是否存在
    boolean newsIsExistById(Integer id);

    //获取指定用户关注的用户发布的稿件列表
    List<Map<String,Object>> getNewsByUserFollow(Integer id,Integer curr,Integer limit);

    //获取头条列表
    List<Map<String,Object>> getTopNews();

    //获取首页新闻列表
    List<Map<String,Object>> getNewsList(Integer curr,Integer limit);

    //获取指定板块的新闻列表
    List<Map<String,Object>> getNewsListByPlateId(Integer id,Integer curr,Integer limit);

    //获取指定分类的新闻列表
    List<Map<String,Object>> getNewsListByClassId(Integer id,Integer curr,Integer limit);

    //获取指定文章的内容
    Map<String,Object> getNewsContentById(Integer id);

    //获取月周日榜单新闻列表
    Map<String,Object> getRankList();

    //通过文章Id获取相关推荐列表
    List<Map<String,Object>> getRecommendListById(Integer id);

    //通过ID获取已退回的文章详情
    Map<String,Object> getReturnContentById(Integer userId,Integer newsId);

}
