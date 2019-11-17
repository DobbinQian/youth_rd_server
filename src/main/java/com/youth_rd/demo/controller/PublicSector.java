package com.youth_rd.demo.controller;

import com.youth_rd.demo.domain.User;
import com.youth_rd.demo.service.NewsService;
import com.youth_rd.demo.service.PlateAndClassService;
import com.youth_rd.demo.service.SearchService;
import com.youth_rd.demo.service.UserService;
import com.youth_rd.demo.tools.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PublicSector {

    @Autowired
    private PlateAndClassService plateAndClassService;

    @Autowired
    private UserService userService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private NewsService newsService;

    //获取公共板块信息
    @RequestMapping("/getPS")
    public ServerResponse getPS(HttpServletRequest request){
        System.out.println("/getPS");
        Map<String,Object> resultMap = new HashMap<>();
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if(null == user){
            resultMap.put("plate",plateAndClassService.getPlate());
        }else {
            resultMap.put("plate",plateAndClassService.getPlateByUserId(user.getId()));
            resultMap.put("user",userService.getPSUserData(user.getId()));
        }
        return ServerResponse.createBySuccess("获取公共板块信息成功",resultMap);
    }

    //获取动态
    @RequestMapping("/user/getActionList")
    public ServerResponse getActionList(@RequestParam("id") Integer id,
                                        @RequestParam("curr")Integer curr,
                                        @RequestParam("limit")Integer limit){
        System.out.println("/user/getActionList");
        List<Map<String,Object>> resultList = newsService.getNewsByUserFollow(id,curr,limit);
        return ServerResponse.createBySuccess("获取动态成功",resultList);
    }

    //获取热门搜索
    @RequestMapping("/getHotSearch")
    public ServerResponse getHotSearch(){
        List<Map<String,Object>> resultList = searchService.getHotSearch();
        return ServerResponse.createBySuccess("获取热门搜索关键词成功",resultList);
    }
}
