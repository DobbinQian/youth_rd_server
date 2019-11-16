package com.youth_rd.demo.controller;

import com.youth_rd.demo.domain.User;
import com.youth_rd.demo.service.CommentService;
import com.youth_rd.demo.service.InformationService;
import com.youth_rd.demo.service.NewsService;
import com.youth_rd.demo.tools.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class ContentPage {

    @Autowired
    NewsService newsService;

    @Autowired
    CommentService commentService;

    @Autowired
    InformationService informationService;

    //获取文章正文
    @RequestMapping("/getArticle")
    public ServerResponse getArticle(@RequestParam("id") Integer id,@RequestParam("userId") Integer userId){
        //TODO 增加历史记录
        Map<String,Object> resultMap = newsService.getNewsContentById(id);
        return ServerResponse.createBySuccess("成功获取正文",resultMap);
    }

    //获取月、周、日榜单
    @RequestMapping("/getList")
    public ServerResponse getList(){
        Map<String,Object> resultMap = newsService.getRankList();
        return ServerResponse.createBySuccess("获取榜单成功",resultMap);
    }

    //获取相关推荐
    @RequestMapping("/getRecommendList")
    public ServerResponse getRecommendList(@RequestParam("id") Integer id){
        List<Map<String,Object>> resultList = newsService.getRecommendListById(id);
        return ServerResponse.createBySuccess("获取推荐列表成功",resultList);
    }

    //获取评论信息
    @RequestMapping("/getCommentsList")
    public ServerResponse getCommentsList(@RequestParam("id") Integer id){
        List<Map<String,Object>> resultList = commentService.getCommentByNewsId(id);
        return ServerResponse.createBySuccess("获取评论列表成功",resultList);
    }

    //评论
    @RequestMapping(value = "/user/comment",method = RequestMethod.POST)
    public ServerResponse comment(@RequestBody Map<String,String> jsonObj,
                                  HttpServletRequest request){
        Integer id = Integer.valueOf(jsonObj.get("id"));
        Integer cid = Integer.valueOf(jsonObj.get("cid"));
        String content = jsonObj.get("content");
        if(!newsService.newsIsExistById(id)){
            return ServerResponse.createByError("指定新闻不存在");
        }
        int result=0;
        User user = (User) request.getSession().getAttribute("user");
        if(cid>0){
            if(!commentService.commentIsExistById(cid)){
                return ServerResponse.createByError("指定评论不存在");
            }
            result = commentService.commentByNewsIdAndCId(id,user.getId(),cid,content);
        }else{
            result = commentService.commentByNewsId(id,user.getId(),content);
        }
        if(result==0){
            return ServerResponse.createByError("数据库异常，评论失败");
        }
        return ServerResponse.createByCheckSuccess();
    }

    //反馈投诉
    @RequestMapping(value = "/user/feedback",method = RequestMethod.POST)
    public ServerResponse feedback(@RequestBody Map<String,String> jsonObj,
                                   HttpServletRequest request){
        Integer id = Integer.valueOf(jsonObj.get("id"));
        Integer op = Integer.valueOf(jsonObj.get("op"));
        String content = jsonObj.get("content");
        if(!newsService.newsIsExistById(id)){
            return ServerResponse.createByError("指定新闻不存在");
        }
        User user = (User) request.getSession().getAttribute("user");
        int result = informationService.feedbackByNewsId(id,user.getId(),content);
        if(result==0){
            return ServerResponse.createByError("数据库异常，评论失败");
        }
        return ServerResponse.createByCheckSuccess();
    }
}
