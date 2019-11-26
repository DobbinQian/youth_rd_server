package com.youth_rd.demo.controller;

import com.youth_rd.demo.service.UserManageService;
import com.youth_rd.demo.tools.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AdminManageUser {

    @Autowired
    UserManageService userManageService;
    //获取用户列表信息
    @RequestMapping("/adm/getUserList")
    public ServerResponse getUserList(@RequestBody Map<String,String> jsonObj){
        String id = jsonObj.get("id");
        String email = jsonObj.get("email");
        String name = jsonObj.get("name");
        String curr = jsonObj.get("curr");
        String limit = jsonObj.get("limit");
        String errStr = "";
        if(curr==null){
            errStr+="curr为null";
        }
        if(limit==null){
            errStr+="limit为null";
        }
        if(errStr.length()>0){
            return ServerResponse.createByError(errStr);
        }
        if(email!=null&&email.equals("")){
            email=null;
        }
        if(name!=null&&name.equals("")){
            name=null;
        }
        Integer iId = id==null||id.equals("")?null:Integer.valueOf(id);
        List<Map<String,Object>> resultList = userManageService.getAllUser(email, name, iId, Integer.valueOf(curr), Integer.valueOf(limit));
        String number = String.valueOf(resultList.get(0).get("number"));
        resultList.remove(resultList.get(0));
        return ServerResponse.createBySuccess(number,resultList);
    }

    //封禁/取消封禁
    @RequestMapping(value = "/adm/banOrReCover",method = RequestMethod.POST)
    public ServerResponse banOrReCover(@RequestBody Map<String,String> jsonObj){


        String id = jsonObj.get("id");
        String op = jsonObj.get("op");
        String errStr = "";
        if(id==null){
            errStr+="id为null";
        }
        if(op==null){
            errStr+="op为null";
        }
        if(errStr.length()>0){
            System.out.println("id:"+id+"--"+"op:"+op);
            return ServerResponse.createByError(errStr);
        }
        int result = userManageService.banOrRecover(Integer.valueOf(id), Integer.valueOf(op));
        if(result==0){
            return ServerResponse.createByError("数据库异常，操作失败");
        }
        return ServerResponse.createByCheckSuccess();
    }
}
