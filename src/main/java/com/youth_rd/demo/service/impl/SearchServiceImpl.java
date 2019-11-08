package com.youth_rd.demo.service.impl;

import com.youth_rd.demo.service.SearchService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {
    @Override
    public List<Map<String, Object>> getHotSearch() {
        return null;
    }

    @Override
    public int addKeyword(String keyword) {
        return 0;
    }
}
