package com.youth_rd.demo.controller;

import com.youth_rd.demo.service.PlateAndClassService;
import com.youth_rd.demo.tools.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AdminManagePlates {

    @Autowired
    PlateAndClassService plateAndClassService;

    //获取板块或分类信息
    @RequestMapping("/adm/getPlateAndClassAdmin")
    public ServerResponse getPlateAndClassAdmin(){
        List<Map<String,Object>> resultList = plateAndClassService.getAllPacToAdm();
        return ServerResponse.createBySuccess("管理员获取板块和分类信息成功",resultList);
    }

    //添加板块或分类
    @RequestMapping(value = "/adm/addPlateOrClass",method = RequestMethod.POST)
    public ServerResponse addPlateOrClass(@RequestBody Map<String,String> jsonObj){
        Integer id = Integer.valueOf(jsonObj.get("id"));
        Integer op = Integer.valueOf(jsonObj.get("op"));
        String name = jsonObj.get("name");

        int result = 0;
        if(op==1){
            result = plateAndClassService.addPlate(name);
        }else if(op==2){
            result = plateAndClassService.addClass(id,name);
        }else{
            return ServerResponse.createByError("操作码错误");
        }
        if(result==0){
            return ServerResponse.createByError("数据库异常，操作失败");
        }
        return ServerResponse.createByCheckSuccess();
    }

    //删除/恢复板块或分类
    @RequestMapping(value = "/adm/deleteOrRecoverPlate",method = RequestMethod.POST)
    public ServerResponse deleteOrRecoverPlate(@RequestBody Map<String,String> jsonObj){
        Integer id = Integer.valueOf(jsonObj.get("id"));
        Integer op = Integer.valueOf(jsonObj.get("op"));
        int result = 0;
        if(Math.abs(op)==1){
            result = plateAndClassService.deleteOrRecoverPlate(id,op);
        }else if(Math.abs(op)==2){
            result = plateAndClassService.deleteOrRecoverClass(id,op);
        }else{
            return ServerResponse.createByError("操作码错误");
        }
        if(result==0){
            return ServerResponse.createByError("数据库异常，操作失败");
        }
        return ServerResponse.createByCheckSuccess();
    }
}
