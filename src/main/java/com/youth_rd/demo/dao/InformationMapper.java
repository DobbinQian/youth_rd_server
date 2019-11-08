package com.youth_rd.demo.dao;

import com.youth_rd.demo.domain.Information;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface InformationMapper {
    //获取所有写给管理员的信息
    List<Information> selectByAdm();
    //增加信息
    int insert(Information information);
}
