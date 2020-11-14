package com.kelly.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kelly.model.Tgoods;

import java.util.Map;

public interface GoodsService {

    void SaveOrUpate(Tgoods tgoods);

    Page<Tgoods> queryPage(Page<Tgoods> objectPage, Map<String, Object> params);

    void changeStaus(String id, String staus);

    void deleteByid(String id);

    void addGoodToCar(String goodsId) throws Exception;
}
