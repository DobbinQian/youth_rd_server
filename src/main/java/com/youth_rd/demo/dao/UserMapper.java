package com.youth_rd.demo.dao;

import com.youth_rd.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component(value = "userMapper")
public interface UserMapper {
    //添加账号
    int insert(User record);

    //通过Id获得User信息
    User selectById(Integer id);

    //通过操作用户Id 和 userId获取
    User selectByOpId(@Param("opId") Integer opId,@Param("id") Integer id);

    //通过Email和Password获得User信息
    User selectByEmailAndPwd(@Param("email") String email,@Param("pwd") String pwd);

    //通过邮箱获取User信息
    User selectByEmail(String email);

    //通过Id修改用户信息
    int updateByUser(User user);

    //通过多项参数查询User列表
    List<User> selectAllByENI(@Param("email") String email,@Param("name") String name,@Param("id") Integer id);

    //通过Id修改Isban属性
    int updateIsBanById(@Param("id") Integer id,@Param("isBan") Integer isBan);
}
