package com.kelly.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kelly.config.Base;
import com.kelly.dao.TybmbMapper;
import com.kelly.model.Tybmb;
import com.kelly.service.YbmbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("ybmbService")
public class YbmbServiceImpl extends Base implements YbmbService {

    @Autowired
    private TybmbMapper tybmbMapper;

    @Override
    public Page<Tybmb> queryPage(Page<Tybmb> page, Map<String, Object> params) {
        List<Tybmb> list = tybmbMapper.queryPage(page, params);
        page.setRecords(list);
        return page;
    }

    @Override
    public void SaveOrUpate(Tybmb mb) {
        if (isEmp(mb.getId())) {//如果是新增，判断版本号是否存在
            QueryWrapper<Tybmb> queryWrapper = new QueryWrapper<>();
            List<Tybmb> list = tybmbMapper.selectList(new QueryWrapper<Tybmb>().eq("ybcode", mb.getYbcode()));
            if (list != null && list.size() > 0) {
                throw new RuntimeException("版本号已经存在！");
            }
            mb.setId(getUUID());
            mb.setCdate(new Date());
            mb.setStaus("0");
            mb.setCuser("admin");
            tybmbMapper.insert(mb);
        } else {
            UpdateWrapper<Tybmb> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", mb.getId()).set("ybcode", mb.getYbcode()).set("muser", mb.getYbname());
            tybmbMapper.update(null, updateWrapper);
        }
    }

    @Override
    public void deleteByid(String id) {
        tybmbMapper.deleteById(id);
    }

    @Override
    public void changeStaus(String id, String staus) {
        UpdateWrapper<Tybmb> updateWrapper = new UpdateWrapper<>();
        if ("0".equals(staus)) {
            updateWrapper.eq("id", id).set("staus", "1");
        } else {
            updateWrapper.eq("id", id).set("staus", "0");
        }
        tybmbMapper.update(null, updateWrapper);
    }
}
