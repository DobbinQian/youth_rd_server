package com.youth_rd.demo.service.impl;

import com.youth_rd.demo.dao.UserMapper;
import com.youth_rd.demo.domain.User;
import com.youth_rd.demo.service.UserManageService;
import com.youth_rd.demo.tools.PageTool;
import com.youth_rd.demo.tools.RedisTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserManageServiceImpl implements UserManageService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<Map<String, Object>> getAllUser(String email, String name, Integer id, Integer curr, Integer limit) {
        String key = "allUser_by_"+email+"_"+name+"_"+id;
        List<User> userList;
        if(RedisTool.hasKey(redisTemplate,key)){
            userList = RedisTool.getList(redisTemplate,key);
        }else{
            userList = userMapper.selectAllByENI(email, name, id);
            RedisTool.setList(redisTemplate,key,userList);
        }
        userList = PageTool.getDateByCL(userList,curr,limit);
        List<Map<String,Object>> resultList = new ArrayList<>();
        for(User u:userList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",u.getId());
            map.put("name",u.getName());
            map.put("date",u.getRegisterDate().toString());
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public int banOrRecover(Integer id, Integer op) {
        if(op==1){
            return userMapper.updateIsBanById(id,1);
        }
        return userMapper.updateIsBanById(id,0);
    }
}
