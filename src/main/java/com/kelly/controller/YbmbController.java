package com.kelly.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kelly.config.Base;
import com.kelly.model.Tybmb;
import com.kelly.service.YbmbService;
import com.kelly.vo.AjaxReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
public class YbmbController extends Base {


    @Autowired
    private YbmbService ybmbService;

    @RequestMapping(value = "/ybmb", method = RequestMethod.POST)
    public Object addEdtion(@RequestBody Tybmb mb, HttpServletRequest req) {
        try {
            ybmbService.SaveOrUpate(mb);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxReturn.error(e.getMessage());
        }
        return AjaxReturn.success(mb);
    }

    @RequestMapping(value = "/ybmb/{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable String id, HttpServletRequest req) {
        try {
            ybmbService.deleteByid(id);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxReturn.error(e.getMessage());
        }
        return AjaxReturn.success();
    }

    @RequestMapping(value = "/changeStaus", method = RequestMethod.PUT)
    public Object changeStaus(@RequestBody Tybmb mb, HttpServletRequest req) {
        if (!isEmp(mb) && isEmp(mb.getYbcode())) {// 当code为空的时候，只存在status和class
            try {
                ybmbService.changeStaus(mb.getId(), mb.getStaus());
            } catch (Exception e) {
                e.printStackTrace();
                return AjaxReturn.error("保存失败");
            }
        }
        return AjaxReturn.success("更新成功");
    }

    @GetMapping("/mbpage")
    public Object page(HttpServletRequest request){
        String current = request.getParameter("current");
        String size = request.getParameter("size");
        Map<String, Object> params = new HashMap<>();
        String codeOrName = request.getParameter("codeOrName");
        if (isEmp(current)) {
            current = "1";
        }
        if (isEmp(size)) {
            size = "10";
        }
        if (!isEmp(codeOrName)) {
            params.put("codeOrName", codeOrName);
        }
        try {
            Page<Tybmb> page = ybmbService
                    .queryPage(new Page<>(Integer.valueOf(current), Integer.valueOf(size)), params);
            return AjaxReturn.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxReturn.error("查询失败");
        }
    }


}
