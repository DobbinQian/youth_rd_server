package com.youth_rd.demo.service.impl;

import com.youth_rd.demo.dao.CommentMapper;
import com.youth_rd.demo.domain.Comment;
import com.youth_rd.demo.service.CommentService;
import com.youth_rd.demo.tools.RedisTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    CommentMapper commentMapper;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public List<Map<String, Object>> getCommentByNewsId(Integer id) {
        String key = "commentsList_"+id;
        List<Comment> commentList;
        if (RedisTool.hasKey(redisTemplate,key)){
            commentList = RedisTool.getList(redisTemplate,key);
        }else{
            commentList = commentMapper.selectByNewsId(id);
            RedisTool.setList(redisTemplate,key,commentList);
        }
        List<Map<String,Object>> resultList = new ArrayList<>();
//
//        for(Comment c : commentList){
//            System.out.println(c.getId());
//            if(c.getUser()==null){
//                System.out.println("为空null");
//            }else{
//                System.out.println(c.getUser().getName());
//            }
//            if(c.getReplyUser()==null){
//                System.out.println("为空null");
//            }else{
//                System.out.println(c.getReplyUser().getName());
//            }
//        }
        //resultList = CommentListFormat.format(commentList,null);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Iterator<Comment> it = commentList.iterator();
        while (it.hasNext()){
            Map<String,Object> map = new HashMap<>();
            Comment c = it.next();
            if(c.getReplyId()==null){
                Map<String,Object> userMap = new HashMap<>();
                userMap.put("id",c.getUser().getId());
                userMap.put("name",c.getUser().getName());
                userMap.put("img",c.getUser().getImage());
                map.put("user",userMap);
                map.put("id",c.getId());
                map.put("cid",c.getReplyId());
                map.put("content",c.getContent());
                map.put("date",formatter.format(c.getTime()));
                resultList.add(map);
                it.remove();
            }
        }
        for(Map<String,Object> m : resultList){
            List<Integer> replyId = new ArrayList<>();
            replyId.add((Integer) m.get("id"));
            it = commentList.iterator();
            List<Map<String,Object>> replyList = new ArrayList<>();
            while (it.hasNext()){
                Map<String,Object> map = new HashMap<>();
                Comment c = it.next();
                if(replyId.contains(c.getReplyId())){
                    Map<String,Object> userMap = new HashMap<>();
                    userMap.put("id",c.getUser().getId());
                    userMap.put("name",c.getUser().getName());
                    userMap.put("img",c.getUser().getImage());
                    map.put("user",userMap);
                    Map<String,Object> replyMap = new HashMap<>();
                    replyMap.put("id",c.getReplyUser().getId());
                    replyMap.put("name",c.getReplyUser().getName());
                    map.put("replyUser",replyMap);
                    map.put("id",c.getId());
                    map.put("cid",c.getReplyId());
                    map.put("replyId",c.getReplyId());
                    map.put("content",c.getContent());
                    map.put("date",formatter.format(c.getTime()));
                    replyId.add(c.getId());
                    replyList.add(map);
                    it.remove();
                }
            }
            m.put("reply",replyList);
        }

        return resultList;
    }

    @Override
    public boolean commentIsExistById(Integer id) {
        Comment comment = commentMapper.selectById(id);
        return comment==null?false:true;
    }

    @Override
    public Map<String,Object> commentByNewsId(Integer newsId, Integer userId, String content) {
        Map<String,Object> resultMap = new HashMap<>();
        Comment comment = new Comment();
        comment.setNewsId(newsId);
        resultMap.put("newsId",comment.getNewsId());
        comment.setUserId(userId);
        resultMap.put("userId",comment.getUserId());
        comment.setContent(content);
        resultMap.put("content",comment.getContent());
        comment.setTime(new Date());
        resultMap.put("time",formatter.format(comment.getTime()));
        if(commentMapper.insert(comment)==0){
            return null;
        }
        return resultMap;
    }

    @Override
    public Map<String,Object> commentByNewsIdAndCId(Integer newsId, Integer userId, Integer commentId, String content) {
        Map<String,Object> resultMap = new HashMap<>();
        Comment comment = new Comment();
        comment.setNewsId(newsId);
        resultMap.put("newsId",comment.getNewsId());
        comment.setUserId(userId);
        resultMap.put("userId",comment.getUserId());
        comment.setContent(content);
        resultMap.put("content",comment.getContent());
        comment.setReplyId(commentId);
        resultMap.put("cid",comment.getReplyId());
        comment.setTime(new Date());
        resultMap.put("time",formatter.format(comment.getTime()));
        if(commentMapper.insert(comment)==0){
            return null;
        }
        return resultMap;
    }
}
