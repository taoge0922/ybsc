package com.kelly.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelly.model.Tcar;

public interface TcarMapper extends BaseMapper<Tcar> {
    int insert(Tcar record);
}