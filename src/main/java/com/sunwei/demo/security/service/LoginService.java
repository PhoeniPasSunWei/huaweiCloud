package com.sunwei.demo.security.service;

import com.sunwei.demo.result.Result;
import com.sunwei.demo.secKillDemo.SeckillUserS.entity.SeckillUser;

public interface LoginService {
    public Result login(SeckillUser user);
    public Result logout();
}
