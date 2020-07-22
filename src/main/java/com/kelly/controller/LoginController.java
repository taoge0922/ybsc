package com.kelly.controller;

import com.kelly.dao.StuMapper;
import com.kelly.model.Tuser;
import com.kelly.service.LoginService;
import com.kelly.vo.AjaxReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/hi")
    public String hi(HttpServletRequest req) {
        return "OK";
    }

    @PostMapping("/login")
    public Object login(@RequestBody Tuser user, HttpServletRequest request){
        Tuser loginUser = loginService.queryByName(user.getUsername());
        if(loginUser!=null&&loginUser.getPassword().equals(user.getPassword())){
            return AjaxReturn.success("登陆成功");
        }else{
            return AjaxReturn.error("登陆错误");
        }

    }
}
