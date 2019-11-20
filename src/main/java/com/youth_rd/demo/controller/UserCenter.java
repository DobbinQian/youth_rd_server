package com.youth_rd.demo.controller;

import com.youth_rd.demo.domain.User;
import com.youth_rd.demo.service.UserService;
import com.youth_rd.demo.tools.RedisTool;
import com.youth_rd.demo.tools.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserCenter {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    //获取基本资料
    @RequestMapping("/user/getPersonData")
    public ServerResponse getPersonData(@RequestParam("id") Integer id){
        User user = RedisTool.getUser(redisTemplate,"user_"+id);
        if(user==null){
            return ServerResponse.createByError("用户未登录");
        }
        Map<String,Object> resultList = userService.getUserData(user.getId());
        return ServerResponse.createBySuccess("获取个人资料成功",resultList);
    }

    //获取关注信息
    @RequestMapping("/user/getFollowList")
    public ServerResponse getFollowList(@RequestParam("id") Integer id,
                                        @RequestParam("curr") Integer curr,
                                        @RequestParam("limit") Integer limit){
        User user = RedisTool.getUser(redisTemplate,"user_"+id);
        if(user==null){
            return ServerResponse.createByError("用户未登录");
        }
        List<Map<String,Object>> resultList = userService.getFollowList(curr,limit,user.getId());
        String number = String.valueOf(resultList.get(0).get("number"));
        resultList.remove(resultList.get(0));
        return ServerResponse.createBySuccess(number,resultList);
    }

    //获取粉丝信息
    @RequestMapping("/user/getFansList")
    public ServerResponse getFansList(@RequestParam("id") Integer id,
                                      @RequestParam("curr") Integer curr,
                                      @RequestParam("limit") Integer limit){
        User user = RedisTool.getUser(redisTemplate,"user_"+id);
        if(user==null){
            return ServerResponse.createByError("用户未登录");
        }
        List<Map<String,Object>> resultList = userService.getFansList(curr,limit,user.getId());
        String number = String.valueOf(resultList.get(0).get("number"));
        resultList.remove(resultList.get(0));
        return ServerResponse.createBySuccess(number,resultList);
    }

    //关注/取消关注
    @RequestMapping(value = "/user/toFollowOrCancel",method = RequestMethod.POST)
    public ServerResponse toFollowOrCancel(@RequestBody Map<String,String> jsonObj){
        String id = jsonObj.get("id");
        String op = jsonObj.get("op");
        String userId = jsonObj.get("userId");

        String errStr = "";

        if(id==null){
            errStr+="id为空_";
        }

        if(op==null){
            errStr+="op为空_";
        }

        if(userId==null){
            errStr+="userId为空_";
        }
        if(errStr.length()>0){
            return ServerResponse.createByError(errStr);
        }
        boolean isFollow = userService.isFollow(Integer.valueOf(id),Integer.valueOf(userId));
        if(isFollow&&Integer.valueOf(op)>0) {
            return ServerResponse.createByError("不要重复关注");
        }
        if(isFollow==false&&Integer.valueOf(op)<0){
            return ServerResponse.createByError("你还没有关注");
        }

        User user = RedisTool.getUser(redisTemplate,"user_"+userId);
        if(user==null){
            return ServerResponse.createByError("用户未登录");
        }
        int result = userService.followOrCancel(Integer.valueOf(id),user.getId(),Integer.valueOf(op));
        if(result==0){
            return ServerResponse.createByError("数据库异常，操作失败");
        }
        return ServerResponse.createByCheckSuccess();
    }

    //获取浏览历史
    @RequestMapping("/user/getBrowseList")
    public ServerResponse getBrowseList(@RequestParam("id") Integer id,
                                        @RequestParam("curr") Integer curr,
                                        @RequestParam("limit") Integer limit){
        User user = RedisTool.getUser(redisTemplate,"user_"+id);
        if(user==null){
            return ServerResponse.createByError("用户未登录");
        }
        List<Map<String,Object>> resultList = userService.getHistoryList(curr,limit,user.getId());
        String number = String.valueOf(resultList.get(0).get("number"));
        resultList.remove(resultList.get(0));
        return ServerResponse.createBySuccess(number,resultList);
    }

    //修改个人信息
    @RequestMapping(value = "/user/editUserData",method = RequestMethod.POST)
    public ServerResponse editUserData(@RequestBody Map<String,String> jsonObj){
        String id = jsonObj.get("id");
        String img = jsonObj.get("img");
        String name = jsonObj.get("name");
        String sex = jsonObj.get("sex");


        if(id==null){
            return ServerResponse.createByError("id参数为空");
        }
        User user = RedisTool.getUser(redisTemplate,"user_"+id);
        if(user==null){
            return ServerResponse.createByError("用户未登录");
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
