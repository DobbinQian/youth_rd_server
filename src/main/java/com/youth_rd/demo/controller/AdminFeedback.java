package com.youth_rd.demo.controller;

import com.youth_rd.demo.domain.User;
import com.youth_rd.demo.service.InformationService;
import com.youth_rd.demo.tools.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class AdminFeedback {

    @Autowired
    InformationService informationService;

    //获取稿件反馈列表
    @RequestMapping("/adm/getFeedbackList")
    public ServerResponse getFeedbackList(@RequestParam("curr") Integer curr,
                                          @RequestParam("limit") Integer limit){
        List<Map<String,Object>> resultList = informationService.getFeedbackList(curr,limit);
        return ServerResponse.createBySuccess("获取反馈列表成功",resultList);
    }

    //回复反馈用户
    @RequestMapping(value = "/adm/replyFeedback",method = RequestMethod.POST)
    public ServerResponse replyFeedback(@RequestBody Map<String,String> jsonObj,
                                        HttpServletRequest request){
        Integer id = Integer.valueOf(jsonObj.get("id"));
        String content = jsonObj.get("content");

        User user = (User)request.getSession().getAttribute("adm");
        int result = informationService.replyFeedbackById(id,user.getId(),content);
        if(result==0){
            return ServerResponse.createByError("数据库异常，操作失败");
        }
        return ServerResponse.createByCheckSuccess();
    }
}
