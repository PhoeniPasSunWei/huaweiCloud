package com.sunwei.demo.security.controller;

import com.sunwei.demo.result.Result;
import com.sunwei.demo.secKillDemo.SeckillUserS.entity.SeckillUser;
import com.sunwei.demo.security.service.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    public LoginServiceImpl loginService;

    @PostMapping("/user/login")
    public Result login(@RequestBody SeckillUser user){
        return loginService.login(user);
    }

    @PostMapping("/user/logout")
    public Result logout(){
        return loginService.logout();
    }

}
