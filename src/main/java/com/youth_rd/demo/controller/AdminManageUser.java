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
    public ServerResponse getUserList(@RequestParam("email") String email,
                                      @RequestParam("name") String name,
                                      @RequestParam("id") Integer id,
                                      @RequestParam("curr") Integer curr,
                                      @RequestParam("limit") Integer limit){
        List<Map<String,Object>> resultList = userManageService.getAllUser(email, name, id, curr, limit);
        return ServerResponse.createBySuccess("获取指定用户列表信息成功",resultList);
    }

    //封禁/取消封禁
    @RequestMapping(value = "/adm/banOrReCover",method = RequestMethod.POST)
    public ServerResponse banOrReCover(@RequestBody Map<String,String> jsonObj){
        Integer id = Integer.valueOf(jsonObj.get("id"));
        Integer op = Integer.valueOf(jsonObj.get("op"));
        int result = userManageService.banOrRecover(id, op);
        if(result==0){
            return ServerResponse.createByError("数据库异常，操作失败");
        }
        return ServerResponse.createByCheckSuccess();
    }
}
