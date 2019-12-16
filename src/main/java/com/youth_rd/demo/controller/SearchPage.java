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
public class SearchPage {

    @Autowired
    NewsService newsService;

    @RequestMapping("/search")
    public ServerResponse search(@RequestParam("op") Integer op,
                                 @RequestParam("data") String data,
                                 @RequestParam("curr") Integer curr,
                                 @RequestParam("limit") Integer limit){
        List<Map<String,Object>> resultList = newsService.getNewsByTC(op,data,curr,limit);
        System.out.println("data:"+data);
        String number = String.valueOf(resultList.get(0).get("number"));
        resultList.remove(resultList.get(0));
        return ServerResponse.createBySuccess(number,resultList);
    }
}
