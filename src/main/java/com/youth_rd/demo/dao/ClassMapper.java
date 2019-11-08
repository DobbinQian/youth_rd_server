package com.youth_rd.demo.dao;

import com.youth_rd.demo.domain.Class;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ClassMapper {
    //通过PlateId获取Class列表
    List<Class> selectNotDeleteByPlateId(Integer id);
    //增加Class
    int insert(@Param("plateId") Integer plateId,@Param("name") String name);
    //修改删除状态
    int updateIsDeleteById(@Param("id") Integer id,@Param("value") Integer value);
}
