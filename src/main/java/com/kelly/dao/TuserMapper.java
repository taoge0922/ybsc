package com.kelly.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelly.model.Tuser;

public interface TuserMapper extends BaseMapper<Tuser> {
    int insert(Tuser record);

    int insertSelective(Tuser record);
}