package com.youth_rd.demo.controller;

import com.youth_rd.demo.domain.User;
import com.youth_rd.demo.service.InformationService;
import com.youth_rd.demo.service.UserService;
import com.youth_rd.demo.tools.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AdminFeedback {

    @Autowired
    InformationService informationService;

    @Autowired
    UserService userService;

    //获取稿件反馈列表
    @RequestMapping("/adm/getFeedbackList")
    public ServerResponse getFeedbackList(@RequestParam("curr") Integer curr,
                                          @RequestParam("limit") Integer limit){
        List<Map<String,Object>> resultList = informationService.getFeedbackList(curr,limit);
        String number = String.valueOf(resultList.get(0).get("number"));
        resultList.remove(resultList.get(0));
        return ServerResponse.createBySuccess(number,resultList);
    }

    //回复反馈用户
    @RequestMapping(value = "/adm/replyFeedback",method = RequestMethod.POST)
    public ServerResponse replyFeedback(@RequestBody Map<String,String> jsonObj){
        String admId = jsonObj.get("admId");
        String id = jsonObj.get("id");
        String content = jsonObj.get("content");
        String userId = jsonObj.get("userId");
        String errStr = "";
        if(admId==null||admId.equals("")){
            errStr+="admId不能为空 ";
        }
        if(id==null||id.equals("")){
            errStr+="id不能为空 ";
        }
        if(content==null||content.equals("")){
            errStr+="content不能为空 ";
        }
        if(userId==null||userId.equals("")){
            errStr+="userId不能为空";
        }
        if(errStr.length()>0){
            return ServerResponse.createByError(errStr);
        }

        User user = userService.getUserById(Integer.valueOf(admId));

        if(user==null){
            return ServerResponse.createByError("不存在该用户");
        }

        int result = informationService.replyFeedbackById(Integer.valueOf(id),Integer.valueOf(userId),user.getId(),content);
        if(result==0){
            return ServerResponse.createByError("数据库异常，操作失败");
        }
        return ServerResponse.createByCheckSuccess();
    }
}
