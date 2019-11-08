package com.youth_rd.demo.service;

import java.util.List;
import java.util.Map;

public interface SearchService {
    //获取24小时内的热门搜索
    List<Map<String,Object>> getHotSearch();

    //增加热搜数据
    int addKeyword(String keyword);
}
