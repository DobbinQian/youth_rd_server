package com.youth_rd.demo.controller;

import com.youth_rd.demo.service.NewsManageService;
import com.youth_rd.demo.service.NewsService;
import com.youth_rd.demo.tools.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AdminAudit {
    @Autowired
    NewsService newsService;

    @Autowired
    NewsManageService newsManageService;

    //获取待审核列表
    @RequestMapping("/adm/getAuditList")
    public ServerResponse getAuditList(@RequestParam("curr") Integer curr,
                                       @RequestParam("limit") Integer limit){
        List<Map<String,Object>> resultList = newsManageService.getAllAuditList(curr,limit);
        return ServerResponse.createBySuccess("获取所有审核新闻成功",resultList);
    }

    //获取审阅详情
    @RequestMapping("/adm/getAuditContent")
    public ServerResponse getAuditContent(@RequestParam("id") Integer id){
        Map<String,Object> resultMap = newsService.getNewsContentById(id);
        return ServerResponse.createBySuccess("获取文章内容成功",resultMap);
    }

    //提交审核结果
    @RequestMapping(value = "/adm/submitAuditResult",method = RequestMethod.POST)
    public ServerResponse submitAuditResult(@RequestBody Map<String,String> jsonObj){
        Integer id = Integer.valueOf(jsonObj.get("id"));
        Integer op = Integer.valueOf(jsonObj.get("op"));
        String content = jsonObj.get("content");
        int result = newsManageService.audit(id, op, content);
        if(result==0){
            return ServerResponse.createByError("数据库异常，操作失败");
        }
        return ServerResponse.createByCheckSuccess();
    }
}
