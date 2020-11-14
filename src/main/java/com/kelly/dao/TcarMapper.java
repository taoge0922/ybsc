package com.kelly.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelly.model.Tcar;
import org.apache.ibatis.annotations.Param;

public interface TcarMapper extends BaseMapper<Tcar> {
    int insert(Tcar record);

    int getSumByUsername(@Param("username") String username);
}