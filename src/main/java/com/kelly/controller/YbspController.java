package com.kelly.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kelly.config.Base;
import com.kelly.model.Tgoods;
import com.kelly.service.GoodsService;
import com.kelly.vo.AjaxReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
public class YbspController  extends Base {

    @Autowired
    private GoodsService goodsService;

    @PostMapping("/addSp")
    public Object addSp(@RequestParam(name = "file") MultipartFile file, HttpServletRequest request) {
        String mbid=request.getParameter("mbid");
        String goodsname=request.getParameter("goodsname");
        String goodsnum=request.getParameter("goodsnum");
        String mbname=request.getParameter("mbname");
        String price=request.getParameter("price");
        String staticPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        String fileName = file.getOriginalFilename();  //获取文件名
        String id = getUUID();
        String savePath = staticPath.substring(1) +"/"+ "images/" +id;
        String visPath = "static/images/"+id+"/"+fileName;
        try {
            Path dir = Paths.get(savePath);
            boolean hasDir = Files.exists(dir, LinkOption.NOFOLLOW_LINKS);
            if(!hasDir){
                Files.createDirectories(dir);
            }
            file.transferTo(new File(savePath+"/"+fileName));//将临时存储的文件移动到真实存储路径下
            Tgoods goods = new Tgoods();
            goods.setGoodsname(goodsname);
            goods.setGoodsnum(Integer.valueOf(goodsnum));
            goods.setImageurl(visPath);
            goods.setMbid(mbid);
            goods.setMbname(mbname);
            goods.setPrice(Double.valueOf(price));
            goods.setStaus("0");
            goods.setCuser("admin");
            goods.setCdate(new Date());
            goodsService.SaveOrUpate(goods);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxReturn.success(visPath);
    }

    @RequestMapping(value = "/goods/{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable String id, HttpServletRequest req) {
        try {
            goodsService.deleteByid(id);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxReturn.error(e.getMessage());
        }
        return AjaxReturn.success();
    }

    @RequestMapping(value = "/sell/{id}", method = RequestMethod.POST)
    public Object sell(@PathVariable String id, HttpServletRequest req) {
        try {
            goodsService.addGoodToCar(id);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxReturn.error(e.getMessage());
        }
        return AjaxReturn.success();
    }

    @GetMapping("/goodpage")
    public Object page(HttpServletRequest request){
        String current = request.getParameter("current");
        String size = request.getParameter("size");
        Map<String, Object> params = new HashMap<>();
        String codeOrName = request.getParameter("codeOrName");
        String staus = request.getParameter("staus");
        if (isEmp(current)) {
            current = "1";
        }
        if (isEmp(size)) {
            size = "10";
        }
        if (!isEmp(codeOrName)) {
            params.put("codeOrName", codeOrName);
        }
        if (!isEmp(staus)) {
            params.put("staus", staus);
        }
        try {
            Page<Tgoods> page = goodsService
                    .queryPage(new Page<>(Integer.valueOf(current), Integer.valueOf(size)), params);
            return AjaxReturn.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxReturn.error("查询失败");
        }
    }

    @RequestMapping(value = "/changeGoodsStaus", method = RequestMethod.PUT)
    public Object changeStaus(@RequestBody Tgoods goods, HttpServletRequest req) {
        if (!isEmp(goods) ) {// 当code为空的时候，只存在status和class
            try {
                goodsService.changeStaus(goods.getId(), goods.getStaus());
            } catch (Exception e) {
                e.printStackTrace();
                return AjaxReturn.error("保存失败");
            }
        }
        return AjaxReturn.success("更新成功");
    }


}
