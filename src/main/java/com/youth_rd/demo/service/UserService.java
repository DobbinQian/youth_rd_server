package com.youth_rd.demo.service;


import com.youth_rd.demo.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    //获取用户信息
    Map<String, Object> getUserData(Integer id);

    //获取板块上的用户信息
    Map<String, Object> getPSUserData(Integer id);

    //通过email和pwd获取用户
    User getUserByEmailAndPwd(String email, String pwd);

    //通过邮箱查找用户
    User getUserByEmail(String email);

    //通过ID获取用户
    User getUserById(Integer id);

    //获取用户关注的用户列表
    List<Map<String,Object>> getFollowList(Integer curr,Integer limit,Integer id);

    //获取粉丝列表
    List<Map<String,Object>> getFansList(Integer curr,Integer limit,Integer id);

    //关注/取消关注用户用户
    int followOrCancel(Integer fcId,Integer UserId,Integer Op);

    boolean isFollow(Integer id,Integer userId);

    //获取用户浏览历史
    List<Map<String,Object>> getHistoryList(Integer curr,Integer limit,Integer id);

    //修改用户个人资料
    int updateUserData(User user);

    //通过邮箱密码创建新用户
    int addUserByEmailAndPwd(String email,String pwd);

    //通过邮箱修改用户密码
    int updatePwdByEmail(String email,String pwd);

}

