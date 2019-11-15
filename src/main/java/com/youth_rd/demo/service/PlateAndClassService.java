package com.youth_rd.demo.service;

import java.util.List;
import java.util.Map;

public interface PlateAndClassService {
    //获取用户自定义的板块信息
    String getPlateByUserId(Integer id);

    //获取默认板块信息
    List<Map<String,Object>> getPlate();

    //编辑自定义板块
    int editUserPlate(String value,Integer id);

    //用户获取所有板块和分类信息
    List<Map<String,Object>> getAllPlateAndClass();

    //获取指定板块下的分类信息
    List<Map<String,Object>> getClassByPlateId(Integer id);

    //管理员获取所有板块和分类信息
    List<Map<String,Object>> getAllPacToAdm();

    //添加板块
    int addPlate(String name);

    //添加分类
    int addClass(Integer plateId,String name);

    //删除/恢复板块
    int deleteOrRecoverPlate(Integer id,Integer op);

    //删除/恢复分类
    int deleteOrRecoverClass(Integer id,Integer op);

}
