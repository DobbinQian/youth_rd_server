package com.youth_rd.demo.dao;

import com.youth_rd.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PowerMapper {
    //通过id查找指定用户
    User selectById(Integer id);
}
