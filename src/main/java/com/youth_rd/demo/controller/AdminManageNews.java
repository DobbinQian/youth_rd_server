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
    public ServerResponse getContributeList(@RequestBody Map<String,String> jsonObj){
        String plateId = jsonObj.get("plateId");
        String classId = jsonObj.get("classId");
        String tag = jsonObj.get("tag");
        String title = jsonObj.get("title");
        String username = jsonObj.get("username");
        String newsId = jsonObj.get("newsId");
        String curr = jsonObj.get("curr");
        String limit = jsonObj.get("limit");
        String errStr = "";
        Integer plateIdInt;
        if(plateId==null||plateId.equals("")){
            plateIdInt = null;
        }else{
            plateIdInt = Integer.valueOf(plateId);
        }
        Integer classIdInt;
        if(classId==null||classId.equals("")){
            classIdInt = null;
        }else{
            classIdInt = Integer.valueOf(classId);
        }
        Integer tagInt;
        if(tag==null||tag.equals("")){
            tagInt = null;
        }else{
            tagInt = Integer.valueOf(tag);
        }
        Integer newsIdInt;
        if(newsId==null||newsId.equals("")){
            newsIdInt = null;
        }else{
            newsIdInt = Integer.valueOf(newsId);
        }
        if(curr==null){
            errStr+="curr为空 ";
        }
        if(limit==null){
            errStr+="limit为空 ";
        }
        if(errStr.length()>0){
            return ServerResponse.createByError(errStr);
        }
        List<Map<String,Object>> resultList = newsManageService.admGetNewsList(newsIdInt,
                plateIdInt,classIdInt, title,tagInt,username, Integer.valueOf(curr), Integer.valueOf(limit));
        String number = String.valueOf(resultList.get(0).get("number"));
        resultList.remove(resultList.get(0));
        return ServerResponse.createBySuccess(number,resultList);
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
        System.out.println("/adm/deleteOrRecoverNews");
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
    @RequestMapping(value = "/adm/editTopLine",method = RequestMethod.POST)
    public ServerResponse editTopLine(@RequestBody Map<String,List<Integer>> jsonObj){
        List<Integer> obj = jsonObj.get("data");
        if(obj.size()>5){
            return ServerResponse.createByError("头条数量不符合要求");
        }
        int result = newsManageService.editTopLine(obj);
        if(result==0){
            return ServerResponse.createByError("数据库异常，操作失败");
        }
        return ServerResponse.createByCheckSuccess();
    }
}
