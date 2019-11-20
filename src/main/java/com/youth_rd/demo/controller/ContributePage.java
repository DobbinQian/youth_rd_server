package com.youth_rd.demo.controller;

import com.youth_rd.demo.domain.User;
import com.youth_rd.demo.service.ContributeService;
import com.youth_rd.demo.service.UserService;
import com.youth_rd.demo.tools.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class ContributePage {

    @Autowired
    private ContributeService contributeService;

    @Autowired
    private UserService userService;

    //投稿
    @RequestMapping(value = "/user/contribute",method = RequestMethod.POST)
    public ServerResponse contribute(@RequestBody Map<String,String> jsonObj,
                                     HttpServletRequest request){

        String classId = jsonObj.get("classId");
        String title = jsonObj.get("title");
        String content = jsonObj.get("content");
        String userId = jsonObj.get("userId");
        String img = jsonObj.get("img");

        String errStr = "";

        if(classId==null){
            errStr+="classId为空_";
        }

        if(title==null){
            errStr+="titleId为空_";
        }

        if(content==null){
            errStr+="contentId为空_";
        }

        if(userId==null){
            errStr+="userId为空_";
        }
        if(img==null){
            errStr+="img为空";
        }
        if(errStr.length()>0){
            return ServerResponse.createByError(errStr);
        }
        User user = userService.getUserById(Integer.valueOf(userId));

        int result = contributeService.contribute(title,img,Integer.valueOf(classId),content,user);

        if(result==0){
            return ServerResponse.createByError("数据库错误，投稿失败");
        }

        return ServerResponse.createByCheckSuccess();
    }

    //获取已投稿信息
    @RequestMapping("/user/getContributes")
    public ServerResponse getContributes(@RequestParam("id") Integer id,
                                         @RequestParam("curr") Integer curr,
                                         @RequestParam("limit") Integer limit,
                                         HttpServletRequest request){
        String errStr = "";

        if(id==null){
            errStr+="id为空_";
        }

        if(curr==null){
            errStr+="curr为空_";
        }

        if(limit==null){
            errStr+="limit为空_";
        }
        if(errStr.length()>0){
            return ServerResponse.createByError(errStr);
        }

        User user = userService.getUserById(id);
        List<Map<String,Object>> resultList = contributeService.getContributeList(user.getId(),curr,limit);
        String number = String.valueOf(resultList.get(0).get("number"));
        resultList.remove(resultList.get(0));
        return ServerResponse.createBySuccess(number,resultList);
    }

    //获取审阅中信息
    @RequestMapping("/user/getAuditingList")
    public ServerResponse getAuditList(@RequestParam("id") Integer id,
                                       @RequestParam("curr") Integer curr,
                                       @RequestParam("limit") Integer limit,
                                       HttpServletRequest request){
        String errStr = "";

        if(id==null){
            errStr+="id为空_";
        }

        if(curr==null){
            errStr+="curr为空_";
        }

        if(limit==null){
            errStr+="limit为空_";
        }
        if(errStr.length()>0){
            return ServerResponse.createByError(errStr);
        }
        User user = userService.getUserById(id);
        List<Map<String,Object>> resultList = contributeService.getAuditList(user.getId(),curr,limit);
        String number = String.valueOf(resultList.get(0).get("number"));
        resultList.remove(resultList.get(0));
        return ServerResponse.createBySuccess(number,resultList);
    }

    //获取已退回信息
    @RequestMapping("/user/getReturnList")
    public ServerResponse getReturnList(@RequestParam("id") Integer id,
                                        @RequestParam("curr") Integer curr,
                                        @RequestParam("limit") Integer limit,
                                        HttpServletRequest request){
        String errStr = "";

        if(id==null){
            errStr+="id为空_";
        }

        if(curr==null){
            errStr+="curr为空_";
        }

        if(limit==null){
            errStr+="limit为空_";
        }
        if(errStr.length()>0){
            return ServerResponse.createByError(errStr);
        }
        User user = userService.getUserById(id);
        List<Map<String,Object>> resultList = contributeService.getReturnList(user.getId(),curr,limit);
        String number = String.valueOf(resultList.get(0).get("number"));
        resultList.remove(resultList.get(0));
        return ServerResponse.createBySuccess(number,resultList);
    }
}
