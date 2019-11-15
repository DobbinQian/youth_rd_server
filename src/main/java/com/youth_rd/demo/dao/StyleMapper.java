package com.youth_rd.demo.dao;

import com.youth_rd.demo.domain.Style;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface StyleMapper {
    Style selectByUserId(Integer id);

    int insert(@Param("id") Integer id,@Param("value") String value);

    int updateByUserId(@Param("id") Integer id,@Param("value") String value);

}
