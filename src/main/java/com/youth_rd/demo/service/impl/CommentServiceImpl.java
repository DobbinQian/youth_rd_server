package com.youth_rd.demo.service.impl;

import com.youth_rd.demo.dao.CommentMapper;
import com.youth_rd.demo.domain.Comment;
import com.youth_rd.demo.service.CommentService;
import com.youth_rd.demo.tools.RedisTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    CommentMapper commentMapper;
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
        for(Comment c:commentList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",c.getId());
            map.put("cid",c.getReplyId());
            map.put("content",c.getContent());
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public boolean commentIsExistById(Integer id) {
        Comment comment = commentMapper.selectById(id);
        return comment==null?false:true;
    }

    @Override
    public int commentByNewsId(Integer newsId, Integer userId, String content) {
        Comment comment = new Comment();
        comment.setNewsId(newsId);
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setTime(new Date());
        return commentMapper.insert(comment);
    }

    @Override
    public int commentByNewsIdAndCId(Integer newsId, Integer userId, Integer commentId, String content) {
        Comment comment = new Comment();
        comment.setNewsId(newsId);
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setReplyId(commentId);
        comment.setTime(new Date());
        return commentMapper.insert(comment);
    }
}
