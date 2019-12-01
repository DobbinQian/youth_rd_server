package com.youth_rd.demo.dao;

import com.youth_rd.demo.domain.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface NewsMapper {
    //添加新闻
    int insert(News record);

    //通过id、plateId、classId、tag、title、userId查询新闻
    List<News> selectAllByIPCTTU(@Param("id") Integer id,
                                 @Param("plateId") Integer plateId,
                                 @Param("classId") Integer classId,
                                 @Param("tag") Integer tag,
                                 @Param("title") String title,
                                 @Param("username") String username);

    //通过Id获得新闻信息
    News selectById(Integer id);

    //通过ID获取被退回的新闻
    News selectReturnContentById(@Param("userId") Integer userId,@Param("newsId") Integer newsId);

    //获取指定用户的以通过审核的稿件列表
    List<News> selectPassByUserId(Integer id);

    //获取指定用户的审核中的稿件列表
    List<News> selectAuditByUserId(Integer id);

    //获取指定用户已退回的稿件列表
    List<News> selectReturnByUserId(Integer id);

    //通过用户ID获取用户关注的人发布的新闻列表
    List<News> selectByFansId(Integer id);

    //获取头条新闻
    List<News> selectByTop();

    //获取所有新闻列表
    List<News> selectAll();

    //通过PlateId获取新闻列表
    List<News> selectByPlateId(Integer id);

    //通过ClassId获取新闻列表
    List<News> selectByClassId(Integer id);

    //获取一天内点击数量排序的列表
    List<News> selectByDayBrowse();

    //获取一天内评论数量排序的列表
    List<News> selectByDayComments();

    //获取一周内点击数量排序的列表
    List<News> selectByWeekBrowse();

    //获取一周内评论数量排序的列表
    List<News> selectByWeekComments();

    //获取一个月内点击数量排序的列表
    List<News> selectByMonthBrows();

    //获取一个月内评论数量排序的列表
    List<News> selectByMonthComments();

    //获取所有审核中的新闻列表
    List<News> selectAllAuditList();

    //修改新闻Tag
    int updateTagById(@Param("id") Integer id,@Param("tag") Integer tag,@Param("content") String content);

    //通过ID或标题获取新闻列表
    List<News> selectByIdTitle(@Param("id") Integer id,@Param("title") String title);

    //获取头条数量
    int selectTopSum();
    //获取头条列表
    List<News> selectTopLine();
    //修改头条列表
    int updateTopLineList(@Param("obj") List<Integer> obj);
    //设置头条
    int updateTopLine(@Param("id") Integer id,@Param("topImg") String topImg,@Param("queue") Integer queue);
    //删除或恢复新闻
    int updateIsDelete(@Param("id") Integer id,@Param("op") Integer op);

    //更新投稿
    int updateAllById(News news);
}

