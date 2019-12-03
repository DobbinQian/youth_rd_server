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
        String op = jsonObj.get("op");
        String name = jsonObj.get("name");
        String errStr = "";
        if(name==null||name.equals("")){
            errStr+="name为空 ";
        }
        if(op==null||op.equals("")){
            errStr+="op为空 ";
        }
        if(errStr.length()>0){
            return ServerResponse.createByError(errStr);
        }
        int result = 0;
        Integer iOp = Integer.valueOf(op);
        if(iOp>0){
            result = plateAndClassService.addPlate(name);
        }else{
            result = plateAndClassService.addClass(Math.abs(iOp),name);
        }
        if(result==0){
            return ServerResponse.createByError("数据库异常，操作失败");
        }
        return ServerResponse.createByCheckSuccess();
    }

    //删除/恢复板块或分类
    @RequestMapping(value = "/adm/deleteOrRecoverPlate",method = RequestMethod.POST)
    public ServerResponse deleteOrRecoverPlate(@RequestBody Map<String,String> jsonObj){
        String id = jsonObj.get("id");
        String op = jsonObj.get("op");
        String errStr = "";
        if(id==null||id.equals("")){
            errStr+="id为空 ";
        }
        if(op==null||op.equals("")){
            errStr+="op为空 ";
        }
        if(errStr.length()>0){
            return ServerResponse.createByError(errStr);
        }
        int result = 0;
        Integer iOp = Integer.valueOf(op);
        Integer iId = Integer.valueOf(id);
        if(Math.abs(iOp)==1){
            result = plateAndClassService.deleteOrRecoverPlate(iId,iOp);
        }else if(Math.abs(iOp)==2){
            result = plateAndClassService.deleteOrRecoverClass(iId,iOp);
        }else{
            return ServerResponse.createByError("操作码错误");
        }
        if(result==0){
            return ServerResponse.createByError("数据库异常，操作失败");
        }
        return ServerResponse.createByCheckSuccess();
    }
}
