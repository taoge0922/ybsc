package com.kelly.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kelly.model.Tybmb;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TybmbMapper extends BaseMapper<Tybmb> {
    int insert(Tybmb record);

    List<Tybmb> queryPage(Page<Tybmb> page, @Param("params")Map<String, Object> params);
}