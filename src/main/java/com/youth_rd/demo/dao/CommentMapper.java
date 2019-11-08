package com.youth_rd.demo.dao;

import com.youth_rd.demo.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CommentMapper {
    //通过文章ID获取文章的评论列表
    List<Comment> selectByNewsId(Integer id);
    //通过ID判断评论是否存在
    Comment selectById(Integer id);
    //创建评论
    int insert(Comment comment);
}
