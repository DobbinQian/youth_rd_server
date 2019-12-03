package com.youth_rd.demo.service.impl;

import com.youth_rd.demo.dao.ClassMapper;
import com.youth_rd.demo.dao.PlateMapper;
import com.youth_rd.demo.dao.StyleMapper;
import com.youth_rd.demo.domain.Class;
import com.youth_rd.demo.domain.Plate;
import com.youth_rd.demo.domain.Style;
import com.youth_rd.demo.service.PlateAndClassService;
import com.youth_rd.demo.tools.RedisTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlateAndClassServiceImpl implements PlateAndClassService {

    @Autowired
    StyleMapper styleMapper;

    @Autowired
    PlateMapper plateMapper;

    @Autowired
    ClassMapper classMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public String getPlateByUserId(Integer id) {
        Style style = styleMapper.selectByUserId(id);
        if(style==null){
            styleMapper.insert(id,"");
            return "";
        }
        return style.getValue();
    }

    @Override
    public List<Map<String, Object>> getPlate() {
        String key = "notDeletePlates";
        List<Plate> plateList;
        if(RedisTool.hasKey(redisTemplate,key)){
            plateList = RedisTool.getList(redisTemplate,key);
        }else{
            plateList = plateMapper.selectIsNotDelete();
            RedisTool.setList(redisTemplate,key,plateList);
        }
        List<Map<String,Object>> resultList = new ArrayList<>();
        for(Plate p:plateList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",p.getId());
            map.put("name",p.getName());
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public int editUserPlate(String value, Integer id) {
        return styleMapper.updateByUserId(id,value);
    }

    @Override
    public List<Map<String, Object>> getAllPlateAndClass() {
        String key = "notDeletePlatesAndClasses";
        List<Plate> plateList;
        if(RedisTool.hasKey(redisTemplate,key)){
            plateList = RedisTool.getList(redisTemplate,key);
        }else{
            plateList = plateMapper.selectIsNotDelete();
            RedisTool.setList(redisTemplate,key,plateList);
        }
        List<Map<String,Object>> resultList = new ArrayList<>();
        for(Plate p:plateList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",p.getId());
            map.put("name",p.getName());
            List<Map<String,Object>> mapsList = new ArrayList<>();
            for(Class c:p.getClasses()){
                Map<String,Object> map1 = new HashMap<>();
                map1.put("id",c.getId());
                map1.put("name",c.getName());
                mapsList.add(map1);
            }
            map.put("class",mapsList);
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> getClassByPlateId(Integer id) {
        String key = "classes_"+id;
        List<Class> classList;
        if(RedisTool.hasKey(redisTemplate,key)){
            classList = RedisTool.getList(redisTemplate,key);
        }else{
            classList = classMapper.selectNotDeleteByPlateId(id);
            RedisTool.setList(redisTemplate,key,classList);
        }
        List<Map<String,Object>> resultList = new ArrayList<>();
        for(Class c:classList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",c.getId());
            map.put("name",c.getName());
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> getAllPacToAdm() {
        String key = "PlatesAndClasses";
        List<Plate> plateList;
//        if(RedisTool.hasKey(redisTemplate,key)){
//            plateList = RedisTool.getList(redisTemplate,key);
//        }else{
//            plateList = plateMapper.selectAll();
//            RedisTool.setList(redisTemplate,key,plateList);
//        }
        plateList = plateMapper.selectAll();
        List<Map<String,Object>> resultList = new ArrayList<>();
        for(Plate p:plateList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",p.getId());
            map.put("name",p.getName());
            map.put("isDelete",p.getIsDelete());
            List<Map<String,Object>> mapsList = new ArrayList<>();
            for(Class c:p.getClasses()){
                Map<String,Object> map1 = new HashMap<>();
                map1.put("id",c.getId());
                map1.put("name",c.getName());
                map1.put("isDelete",c.getIsDelete());
                mapsList.add(map1);
            }
            map.put("class",mapsList);
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public int addPlate(String name) {
        return plateMapper.insert(name);
    }

    @Override
    public int addClass(Integer plateId, String name) {
        return classMapper.insert(plateId,name);
    }

    @Override
    public int deleteOrRecoverPlate(Integer id, Integer op) {
        if(op>0){
            return plateMapper.updateIsDeleteById(id,0);
        }else{
            return plateMapper.updateIsDeleteById(id,1);
        }
    }

    @Override
    public int deleteOrRecoverClass(Integer id, Integer op) {
        if(op>0){
            return classMapper.updateIsDeleteById(id,0);
        }else{
            return classMapper.updateIsDeleteById(id,1);
        }
    }
}
