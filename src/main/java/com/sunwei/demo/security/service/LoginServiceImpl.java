package com.sunwei.demo.security.service;

import com.sunwei.demo.result.Result;
import com.sunwei.demo.secKillDemo.SeckillUserS.entity.SeckillUser;
import com.sunwei.demo.secKillDemo.SeckillUserS.mapper.SeckillUserMapper;
import com.sunwei.demo.security.domain.LoginUser;
import com.sunwei.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SeckillUserMapper userMapper;

    @Override
    public Result login(SeckillUser user) {
        //进行用户认证  认证通过 生成一个jwt
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getNikename(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(token);
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }else {
            LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
            String id = loginUser.getUser().getId();
            String jwt = JwtUtil.createJWT(id);
            return Result.ok(jwt);
        }
    }

    public Result logout() {
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        SeckillUser seckillUser = (SeckillUser) token.getPrincipal();
        //清除 redis 中的缓存
        seckillUser.setNikename("zhangSanOut");
        userMapper.updateById(seckillUser);
        return Result.ok("注销成功");
    }
}
