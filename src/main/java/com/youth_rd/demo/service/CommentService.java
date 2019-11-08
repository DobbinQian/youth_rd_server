package com.youth_rd.demo.service;

import java.util.List;
import java.util.Map;

public interface CommentService {
    //获取指定文章的评论信息
    List<Map<String,Object>> getCommentByNewsId(Integer id);

    //通过评论ID判断评论是否存在
    boolean commentIsExistById(Integer id);

    //在指定文章中评论
    int commentByNewsId(Integer newsId,Integer userId,String content);

    //在指定文章中评论指定评论
    int commentByNewsIdAndCId(Integer newsId,Integer userId,Integer commentId,String content);
}
