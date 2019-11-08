package com.youth_rd.demo.dao;

import com.youth_rd.demo.domain.History;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component(value = "historyMapper")
public interface HistoryMapper {
    //增加历史记录
    int insert(@Param("userId") Integer userId,@Param("newsId") Integer newsId,@Param("time") Date time);

    //删除历史记录
    int delete(Integer id);

    //通过UserID获取浏览历史
    List<History> selectByUserId(Integer id);
}
