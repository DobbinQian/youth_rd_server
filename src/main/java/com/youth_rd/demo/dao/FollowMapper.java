package com.youth_rd.demo.dao;

import com.youth_rd.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "followMapper")
public interface FollowMapper {
    //获取关注的人列表
    List<User> selectFollowsById(Integer id);
    //获取粉丝列表
    List<User> selectFansById(Integer id);
    //增加follow
    int insert(@Param("followId") Integer followId,@Param("followedId") Integer followedId);
    //删除follow
    int delete(@Param("followId") Integer followId,@Param("followedId") Integer followedId);
    //查看是否有关注信息
    User selectByFollowsId(@Param("id") Integer id,@Param("userId") Integer userId);
}
