package com.youth_rd.demo.service;

import java.util.List;
import java.util.Map;

public interface NewsManageService {
    //获取所有待审核列表
    List<Map<String,Object>> getAllAuditList(Integer curr, Integer limit);

    //审核
    int audit(Integer id,Integer op,String content);

    //获取所有通过和被删除的稿件
    List<Map<String,Object>> admGetNewsList(Integer id,Integer plateId,Integer classId, String title,Integer tag,String username, Integer curr, Integer limit);

    //返回头条数量
    int sumOfTop();

    //编辑头条
    int editTopLine(List<Integer> obj);

    //设置为头条
    int setTopLine(Integer id,String topImg,Integer queue);

    //删除/恢复稿件
    int deleteOrRecover(Integer id,Integer op);

    //获取头条列表
    List<Map<String,Object>> getTopLine();
}
