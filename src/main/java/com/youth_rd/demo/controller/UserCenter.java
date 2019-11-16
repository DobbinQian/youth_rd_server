package com.youth_rd.demo.controller;

import com.youth_rd.demo.domain.User;
import com.youth_rd.demo.service.UserService;
import com.youth_rd.demo.tools.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class UserCenter {
    @Autowired
    private UserService userService;

    //获取基本资料
    @RequestMapping("/user/getPersonData")
    public ServerResponse getPersonData(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        Map<String,Object> resultList = userService.getUserData(user.getId());
        return ServerResponse.createBySuccess("获取个人资料成功",resultList);
    }

    //获取关注信息
    @RequestMapping("/user/getFollowList")
    public ServerResponse getFollowList(@RequestParam("curr") Integer curr,
                                        @RequestParam("limit") Integer limit,
                                        HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        List<Map<String,Object>> resultList = userService.getFollowList(curr,limit,user.getId());
        return ServerResponse.createBySuccess("获取关注信息成功",resultList);
    }

    //获取粉丝信息
    @RequestMapping("/user/getFansList")
    public ServerResponse getFansList(@RequestParam("curr") Integer curr,
                                      @RequestParam("limit") Integer limit,
                                      HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        List<Map<String,Object>> resultList = userService.getFansList(curr,limit,user.getId());
        return ServerResponse.createBySuccess("获取粉丝信息成功",resultList);
    }

    //关注/取消关注
    @RequestMapping(value = "/user/toFollowOrCancel",method = RequestMethod.POST)
    public ServerResponse toFollowOrCancel(@RequestBody Map<String,String> jsonObj,
                                           HttpServletRequest request){
        Integer id = Integer.valueOf(jsonObj.get("id"));
        Integer op = Integer.valueOf(jsonObj.get("op"));

        //TODO 判断是否已经关注或者没有关注

        User user = (User)request.getSession().getAttribute("user");
        int result = userService.followOrCancel(id,user.getId(),op);
        if(result==0){
            return ServerResponse.createByError("数据库异常，操作失败");
        }
        return ServerResponse.createByCheckSuccess();
    }

    //获取浏览历史
    @RequestMapping("/user/getBrowseList")
    public ServerResponse getBrowseList(@RequestParam("curr") Integer curr,
                                        @RequestParam("limit") Integer limit,
                                        HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        List<Map<String,Object>> resultList = userService.getHistoryList(curr,limit,user.getId());
        return ServerResponse.createBySuccess("获取用户浏览历史成功",resultList);
    }

    //修改个人信息
    @RequestMapping(value = "/user/editUserData",method = RequestMethod.POST)
    public ServerResponse editUserData(@RequestBody Map<String,String> jsonObj,
                                       HttpServletRequest request){
        String id = jsonObj.get("id");
        String img = jsonObj.get("img");
        String name = jsonObj.get("name");
        String sex = jsonObj.get("sex");


        if(id==null){
            return ServerResponse.createByError("id参数为空");
        }
        User user = userService.getUserById(Integer.valueOf(id));
        if(user==null){
            return ServerResponse.createByError("用户不存在");
        }
        if(img!=null){
            user.setImage(img);
        }
        if(name!=null){
            user.setName(name);
        }
        if(sex!=null){
            user.setSex(Integer.valueOf(sex));
        }
        int result = userService.updateUserData(user);
        if(result==0){
            return ServerResponse.createByError("数据库异常，修改失败");
        }
        return ServerResponse.createByCheckSuccess();
    }
}
