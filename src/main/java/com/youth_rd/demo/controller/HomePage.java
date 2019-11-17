package com.youth_rd.demo.controller;

import com.youth_rd.demo.service.NewsService;
import com.youth_rd.demo.tools.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class HomePage {

    @Autowired
    private NewsService newsService;

    //获取头条信息
    @RequestMapping("/getTopLineHome")
    public ServerResponse getTopLineHome(){
        List<Map<String,Object>> resultList = newsService.getTopNews();
        return ServerResponse.createBySuccess("获取头条新闻成功",resultList);
    }

    //获取新闻列表
    @RequestMapping("/getNewsList")
    public ServerResponse getNewsList(@RequestParam("curr")Integer curr, @RequestParam("limit")Integer limit){
        System.out.println("/getNewsList");
        List<Map<String,Object>> resultList = newsService.getNewsList(curr,limit);
        return ServerResponse.createBySuccess("获取首页新闻成功",resultList);
    }
}
