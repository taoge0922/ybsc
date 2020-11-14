package com.kelly.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kelly.model.Tgoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TgoodsMapper extends BaseMapper<Tgoods> {
    int insert(Tgoods record);

    List<Tgoods> queryPage(Page<Tgoods> objectPage,@Param("params") Map<String, Object> params);
}