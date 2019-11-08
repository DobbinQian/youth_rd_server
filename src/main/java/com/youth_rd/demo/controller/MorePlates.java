package com.youth_rd.demo.controller;

import com.youth_rd.demo.domain.User;
import com.youth_rd.demo.service.PlateAndClassService;
import com.youth_rd.demo.tools.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class MorePlates {

    @Autowired
    PlateAndClassService plateAndClassService;

    //获取所有板块和分类信息
    @RequestMapping("/getPlateAndClass")
    public ServerResponse getPlateAndClass(){
        List<Map<String,Object>> resultList = plateAndClassService.getAllPlateAndClass();
        return ServerResponse.createBySuccess("获取所有板块和分类信息成功",resultList);
    }

    //获取关注的板块信息
    @RequestMapping("/user/getFollowPlate")
    public ServerResponse getFollowPlate(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        List<Map<String,Object>> resultList = plateAndClassService.getPlateByUserId(user.getId());
        return ServerResponse.createBySuccess("获取用户关注的板块信息成功",resultList);

    }

    //编辑关注的板块板块
    @RequestMapping(value = "/user/editFollowPlate",method = RequestMethod.POST)
    public ServerResponse editFollowPlate(@RequestBody List<Map<String,Integer>> objs,
                                          HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        int result = plateAndClassService.editUserPlate(objs,user.getId());
        if(result==0){
            return ServerResponse.createByError("数据库异常，编辑关注板块失败");
        }
        return ServerResponse.createByCheckSuccess();
    }
}
