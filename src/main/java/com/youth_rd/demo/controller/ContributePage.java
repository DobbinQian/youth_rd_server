package com.youth_rd.demo.controller;

import com.youth_rd.demo.domain.User;
import com.youth_rd.demo.service.ContributeService;
import com.youth_rd.demo.tools.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class ContributePage {

    @Autowired
    private ContributeService contributeService;

    //投稿
    @RequestMapping(value = "/user/contribute",method = RequestMethod.POST)
    public ServerResponse contribute(@RequestBody Map<String,String> jsonObj,
                                     HttpServletRequest request){
        Integer classId = Integer.valueOf(jsonObj.get("classId"));
        String title = jsonObj.get("title");
        String content = jsonObj.get("content");

        User user = (User) request.getSession().getAttribute("user");

        int result = contributeService.contribute(title,classId,content,user);

        if(result==0){
            return ServerResponse.createByError("数据库错误，投稿失败");
        }

        return ServerResponse.createByCheckSuccess();
    }

    //获取已投稿信息
    @RequestMapping("/user/getContributes")
    public ServerResponse getContributes(@RequestParam("curr") Integer curr,
                                         @RequestParam("limit") Integer limit,
                                         HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        List<Map<String,Object>> resultList = contributeService.getContributeList(user.getId(),curr,limit);
        return ServerResponse.createBySuccess("获取已投稿新闻成功",resultList);
    }

    //获取审阅中信息
    @RequestMapping("/user/getAuditingList")
    public ServerResponse getAuditList(@RequestParam("curr") Integer curr,
                                       @RequestParam("limit") Integer limit,
                                       HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        List<Map<String,Object>> resultList = contributeService.getAuditList(user.getId(),curr,limit);
        return ServerResponse.createBySuccess("获取审核中新闻成功",resultList);
    }

    //获取已退回信息
    @RequestMapping("/user/getReturnList")
    public ServerResponse getReturnList(@RequestParam("curr") Integer curr,
                                        @RequestParam("limit") Integer limit,
                                        HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        List<Map<String,Object>> resultList = contributeService.getReturnList(user.getId(),curr,limit);
        return ServerResponse.createBySuccess("获取已退回新闻成功",resultList);
    }
}