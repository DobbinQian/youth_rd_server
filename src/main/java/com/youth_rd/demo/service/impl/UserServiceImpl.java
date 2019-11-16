package com.youth_rd.demo.service.impl;

import com.youth_rd.demo.dao.FollowMapper;
import com.youth_rd.demo.dao.HistoryMapper;
import com.youth_rd.demo.dao.UserMapper;
import com.youth_rd.demo.domain.History;
import com.youth_rd.demo.domain.User;
import com.youth_rd.demo.service.UserService;
import com.youth_rd.demo.tools.PageTool;
import com.youth_rd.demo.tools.RedisTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    FollowMapper followMapper;

    @Autowired
    HistoryMapper historyMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String,Object> getUserData(Integer id){
        Map<String,Object> resultMap = new HashMap<>();
        User user = userMapper.selectById(id);
        resultMap.put("id",user.getId());
        resultMap.put("img",user.getImage());
        resultMap.put("name",user.getName());
        resultMap.put("sex",user.getSex());
        resultMap.put("email",user.getEmail());
        return resultMap;
    }

    @Override
    public Map<String, Object> getPSUserData(Integer id) {
        Map<String,Object> resultMap = new HashMap<>();
        User user = userMapper.selectById(id);
        resultMap.put("img",user.getImage());
        resultMap.put("name",user.getName());
        return resultMap;
    }

    @Override
    public User getUserByEmailAndPwd(String email, String pwd) {
        User user = userMapper.selectByEmailAndPwd(email,pwd);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userMapper.selectByEmail(email);
        return user;
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> getFollowList(Integer curr,Integer limit,Integer id) {
        String key = "userFollows_"+id;
        List<User> userList;
        if(RedisTool.hasKey(redisTemplate,key)){
            userList = RedisTool.getList(redisTemplate,key);
        }else{
            userList = followMapper.selectFollowsById(id);
            RedisTool.setList(redisTemplate,key,userList);
        }
        userList = PageTool.getDateByCL(userList,curr,limit);
        List<Map<String,Object>> resultList = new ArrayList<>();
        for (User user:userList) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",user.getId());
            map.put("name",user.getName());
            map.put("img",user.getImage());
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> getFansList(Integer curr,Integer limit,Integer id) {
        String key = "userFans_"+id;
        List<User> userList;
        if(RedisTool.hasKey(redisTemplate,key)){
            userList = RedisTool.getList(redisTemplate,key);
        }else{
            userList = followMapper.selectFansById(id);
            RedisTool.setList(redisTemplate,key,userList);
        }
        userList = PageTool.getDateByCL(userList,curr,limit);
        List<Map<String,Object>> resultList = new ArrayList<>();
        for (User user:userList) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",user.getId());
            map.put("name",user.getName());
            map.put("img",user.getImage());
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public int followOrCancel(Integer fcId, Integer userId, Integer op) {
        int result = 0;
        if(op>0){
            result = followMapper.insert(fcId,userId);
        }else{
            result = followMapper.delete(fcId,userId);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getHistoryList(Integer curr, Integer limit, Integer id) {
        String key = "history_"+id;

        List<History> historyList;
        if(RedisTool.hasKey(redisTemplate,key)){
            historyList = RedisTool.getList(redisTemplate,key);
        }else{
            historyList = historyMapper.selectByUserId(id);
            RedisTool.setList(redisTemplate,key,historyList);
        }

        //分页处理
        historyList = PageTool.getDateByCL(historyList,curr,limit);

        //再组装
        List<Map<String,Object>> resultList = new ArrayList<>();
        for (History h:historyList) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",h.getNewsId());
            map.put("title",h.getNews().getTitle());
            map.put("date",h.getTime());
            resultList.add(map);
        }

        return resultList;
    }

    @Override
    public int updateUserData(User user) {
        int result = userMapper.updateByUser(user);
        return result;
    }

    @Override
    public int addUserByEmailAndPwd(String email, String pwd) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(pwd);
        user.setName("用户"+email);
        user.setSex(2);
        user.setImage("mr.jgp");
        user.setIsBan(0);
        user.setRegisterDate(new Date());
        int result = userMapper.insert(user);
        return result;
    }

    @Override
    public int updatePwdByEmail(String email, String pwd) {
        User user = userMapper.selectByEmail(email);
        if(user==null){
            return 0;
        }
        user.setPassword(pwd);
        int result = userMapper.updateByUser(user);
        return result;
    }

}
