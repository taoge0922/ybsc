package com.kelly.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kelly.model.Tybmb;

import java.util.Map;

public interface YbmbService {

    Page<Tybmb> queryPage(Page<Tybmb> page, Map<String, Object> params);

    void SaveOrUpate(Tybmb mb);

    void deleteByid(String id);

    void changeStaus(String id, String staus);
}
