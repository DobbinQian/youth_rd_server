package com.youth_rd.demo.dao;


import com.youth_rd.demo.domain.Plate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface PlateMapper {
    //获取默认板块
    List<Plate> selectIsNotDelete();

    //获取所有板块
    List<Plate> selectAll();

    //设置用户自定义板块
    int updateById(List<Map<String, Integer>> objs, Integer id);

    //增加Plate
    int insert(String name);

    //修改
    int updateIsDeleteById(@Param("id") Integer id,@Param("isDelete") Integer isDelete);
}
