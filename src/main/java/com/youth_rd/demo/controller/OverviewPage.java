package com.youth_rd.demo.controller;

import com.youth_rd.demo.service.NewsService;
import com.youth_rd.demo.service.PlateAndClassService;
import com.youth_rd.demo.tools.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class OverviewPage {

    @Autowired
    PlateAndClassService plateAndClassService;

    @Autowired
    NewsService newsService;

    //获取板块下的分类信息
    @RequestMapping("/getClassList")
    public ServerResponse getClassList(@RequestParam("id") Integer id){
        List<Map<String,Object>> resultList = plateAndClassService.getClassByPlateId(id);
        return ServerResponse.createBySuccess("获取指定板块下的分类信息成功",resultList);
    }

    //获取指定的新闻列表
    @RequestMapping("/getNewsByClass")
    public ServerResponse getNewsByClass(@RequestParam("id") Integer id,
                                         @RequestParam("curr") Integer curr,
                                         @RequestParam("limit") Integer limit){
        List<Map<String,Object>> resultList = null;
        if(id>0){
            resultList = newsService.getNewsListByClassId(id,curr,limit);
        }else{
            resultList = newsService.getNewsListByPlateId(-id,curr,limit);
        }
        return ServerResponse.createBySuccess("获取新闻列表成功",resultList);
    }
}
