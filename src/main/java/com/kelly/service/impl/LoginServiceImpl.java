package com.kelly.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kelly.dao.TuserMapper;
import com.kelly.model.Tuser;
import com.kelly.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TuserMapper tuserMapper;

    @Override
    public Tuser queryByName(String username) {
        return tuserMapper.selectOne(new QueryWrapper<Tuser>().eq("username",username));
    }
}
