package com.youth_rd.demo.service;

import java.util.List;
import java.util.Map;

public interface UserManageService {
    //通过邮箱名字ID获取所有用户信息列表
    List<Map<String,Object>> getAllUser(String email, String name, Integer id, Integer curr, Integer limit);

    //封禁/解封指定账号
    int banOrRecover(Integer id,Integer op);
}
