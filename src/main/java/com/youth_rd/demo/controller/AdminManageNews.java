package com.youth_rd.demo.controller;

import com.youth_rd.demo.service.NewsManageService;
import com.youth_rd.demo.tools.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AdminManageNews {

    @Autowired
    NewsManageService newsManageService;

    //获取稿件列表
    @RequestMapping("/adm/getContributeList")
    public ServerResponse getContributeList(@RequestParam("id") Integer id,
                                            @RequestParam("title") String title,
                                            @RequestParam("curr") Integer curr,
                                            @RequestParam("limit") Integer limit){
        List<Map<String,Object>> resultList = newsManageService.admGetNewsList(id, title, curr, limit);
        return ServerResponse.createBySuccess("获取稿件列表成功",resultList);
    }

    //设置某稿件为头条
    @RequestMapping(value = "/adm/setTopLine",method = RequestMethod.POST)
    public ServerResponse setTopLine(@RequestBody Map<String,String> jsonObj){
        Integer id = Integer.valueOf(jsonObj.get("id"));
        String img = jsonObj.get("img");
        int queue = newsManageService.sumOfTop();
        if(queue>=5){
            return ServerResponse.createByError("头条数量已满");
        }
        int result = newsManageService.setTopLine(id,img,queue+2);
        if(result==0){
            return ServerResponse.createByError("数据库异常，操作失败");
        }
        return ServerResponse.createByCheckSuccess();
    }

    //删除/恢复稿件
    @RequestMapping(value = "/adm/deleteOrRecoverNews",method = RequestMethod.POST)
    public ServerResponse deleteOrRecoverNews(@RequestBody Map<String,String> jsonObj){
        Integer id = Integer.valueOf(jsonObj.get("id"));
        Integer op = Integer.valueOf(jsonObj.get("op"));
        int result = newsManageService.deleteOrRecover(id, op);
        if(result==0){
            return ServerResponse.createByError("数据库异常，操作失败");
        }
        return ServerResponse.createByCheckSuccess();
    }

    //获取头条列表
    @RequestMapping("/adm/getTopLineNews")
    public ServerResponse getTopLineNews(){
        List<Map<String,Object>> resultList = newsManageService.getTopLine();
        return ServerResponse.createBySuccess("获取头条列表成功",resultList);
    }

    //编辑头条
    //TODO 未完成
    @RequestMapping(value = "/adm/editTopLine",method = RequestMethod.POST)
    public ServerResponse editTopLine(@RequestBody List<Integer> obj){
        int result = newsManageService.editTopLine(obj);
        if(result==0){
            return ServerResponse.createByError("数据库异常，操作失败");
        }
        return ServerResponse.createByCheckSuccess();
    }
}
