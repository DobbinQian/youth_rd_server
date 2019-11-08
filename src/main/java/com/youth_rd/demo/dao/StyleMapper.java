package com.youth_rd.demo.dao;

import com.youth_rd.demo.domain.Style;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface StyleMapper {
    List<Style> selectByUserId(Integer id);
}
