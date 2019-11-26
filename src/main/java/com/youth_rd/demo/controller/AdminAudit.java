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
        String number = String.valueOf(resultList.get(0).get("number"));
        resultList.remove(resultList.get(0));
        return ServerResponse.createBySuccess(number,resultList);
    }

    //获取审阅详情
    @RequestMapping("/adm/getAuditContent")
    public ServerResponse getAuditContent(@RequestParam("id") Integer id){
        System.out.println(id);
        Map<String,Object> resultMap = newsService.getNewsContentById(id);
        return ServerResponse.createBySuccess("获取文章内容成功",resultMap);
    }

    //提交审核结果
    @RequestMapping(value = "/adm/submitAuditResult",method = RequestMethod.POST)
    public ServerResponse submitAuditResult(@RequestBody Map<String,String> jsonObj){
        String id = jsonObj.get("id");
        String op = jsonObj.get("op");
        String content = jsonObj.get("content");
        String errStr = "";
        if(id==null){
            errStr+="id为空 ";
        }
        if(op==null){
            errStr+="op为空 ";
        }
        if(content==null){
            errStr+="content为空 ";
        }
        if(errStr.length()>0){
            return ServerResponse.createByError(errStr);
        }
        int result = newsManageService.audit(Integer.valueOf(id), Integer.valueOf(op), content);
        if(result==0){
            return ServerResponse.createByError("数据库异常，操作失败");
        }
        return ServerResponse.createByCheckSuccess();
    }
}
