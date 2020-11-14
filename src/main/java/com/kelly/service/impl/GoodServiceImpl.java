package com.kelly.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kelly.config.Base;
import com.kelly.dao.TcarMapper;
import com.kelly.dao.TgoodsMapper;
import com.kelly.model.Tcar;
import com.kelly.model.Tgoods;
import com.kelly.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GoodServiceImpl extends Base implements GoodsService {

    @Autowired
    private TgoodsMapper tgoodsMapper;
    @Override
    public void SaveOrUpate(Tgoods tgoods) {
        if (isEmp(tgoods.getId())) {//如果是新增，判断版本号是否存在
            tgoods.setId(getUUID());
            tgoodsMapper.insert(tgoods);
        } else {
            UpdateWrapper<Tgoods> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", tgoods.getId()).set("mbid", tgoods.getMbid())
                    .set("goodsname", tgoods.getGoodsname())
                    .set("goodsnum", tgoods.getGoodsnum())
                    .set("mbname", tgoods.getMbname())
                    .set("price", tgoods.getPrice())
                    .set("imageurl", tgoods.getImageurl());
            tgoodsMapper.update(null, updateWrapper);
        }
    }

    @Override
    public Page<Tgoods> queryPage(Page<Tgoods> objectPage, Map<String, Object> params) {
        List<Tgoods> list = tgoodsMapper.queryPage(objectPage, params);
        objectPage.setRecords(list);
        return objectPage;
    }

    @Override
    public void changeStaus(String id, String staus) {
        UpdateWrapper<Tgoods> updateWrapper = new UpdateWrapper<>();
        if ("0".equals(staus)) {
            updateWrapper.eq("id", id).set("staus", "1");
        } else {
            updateWrapper.eq("id", id).set("staus", "0");
        }
        tgoodsMapper.update(null, updateWrapper);
    }

    @Override
    public void deleteByid(String id) {
        tgoodsMapper.deleteById(id);
    }

    @Autowired
    private TcarMapper tcarMapper;

    @Override
    public void addGoodToCar(String goodsId) throws Exception{
        Tgoods goods = tgoodsMapper.selectById(goodsId);
        if(goods!=null&&goods.getGoodsnum()>1){
            Tcar tcar = null;
            tcar = tcarMapper.selectOne(new QueryWrapper<Tcar>().eq("goodid",goodsId).eq("payed","1"));
            if(tcar!=null){
                tcarMapper.update(null,new UpdateWrapper<Tcar>().eq("id",tcar.getId()).set("goodnum",tcar.getGoodnum()+1));
            }else{
                tcar = new Tcar();
                tcar.setGoodnum(1);
                tcar.setGoodid(goodsId);
                tcar.setGoodname(goods.getGoodsname());
                tcar.setGoodprice(goods.getPrice());
                tcar.setPayed("1");
                tcar.setGoodid(goodsId);
                tcar.setId(getUUID());
                tcar.setUsername("周凯利");
                tcarMapper.insert(tcar);
            }
            tgoodsMapper.update(null,new UpdateWrapper<Tgoods>().eq("id",goodsId).set("goodsnum",goods.getGoodsnum()-1));
        }else {
            throw new RuntimeException("商品有误，或者数量不够!");
        }
    }

    @Override
    public int getCarCount(String username) {
        return tcarMapper.getSumByUsername(username);
    }

    @Override
    public List<Tcar> queryCars(String username) {
        return tcarMapper.selectList(new QueryWrapper<Tcar>().eq("username",username).eq("payed","1"));
    }

    @Override
    public void pay(String ids, String nums) {
        String[] idAry = ids.split(",");
        String[] numAry = nums.split(",");
        int index = 0;
        for(String id:idAry){
            String num = numAry[index++];
            Tcar tcar = tcarMapper.selectOne(new QueryWrapper<Tcar>().eq("goodid",id).eq("payed","1"));
            if(tcar.getGoodnum() != Integer.valueOf(num)){
                Integer size = tcar.getGoodnum()-Integer.valueOf(num);
                Tgoods goods = tgoodsMapper.selectById(id);
                tgoodsMapper.update(null,new UpdateWrapper<Tgoods>().eq("id",id).set("goodsnum",goods.getGoodsnum()+size));
            }
            tcarMapper.deleteById(tcar.getId());
        }
    }

}
