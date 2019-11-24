package com.youth_rd.demo.service;

import com.youth_rd.demo.domain.News;
import com.youth_rd.demo.domain.User;

import java.util.List;
import java.util.Map;

public interface ContributeService {

    //投稿
    int contribute(String title,String img,Integer classId, String content, User author);

    //再投稿
    int reContribute(Integer id,String title,String img,Integer userId,Integer classId,String content,News oldNews);

    //获取已投稿的新闻
    List<Map<String,Object>> getContributeList(Integer id,Integer curr,Integer limit);

    //获取审核中的新闻
    List<Map<String,Object>> getAuditList(Integer id,Integer curr,Integer limit);

    //获取已退回新闻
    List<Map<String,Object>> getReturnList(Integer id,Integer curr,Integer limit);
}
