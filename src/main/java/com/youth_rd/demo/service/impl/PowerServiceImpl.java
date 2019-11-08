package com.youth_rd.demo.service.impl;

import com.youth_rd.demo.dao.PowerMapper;
import com.youth_rd.demo.domain.User;
import com.youth_rd.demo.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PowerServiceImpl implements PowerService {

    @Autowired
    PowerMapper powerMapper;

    @Override
    public boolean isExistByUserId(Integer id) {
        User user = powerMapper.selectById(id);
        if(user==null){
            return false;
        }
        return true;
    }
}
