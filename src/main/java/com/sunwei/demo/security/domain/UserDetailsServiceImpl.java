package com.sunwei.demo.security.domain;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sunwei.demo.secKillDemo.SeckillUserS.entity.SeckillUser;
import com.sunwei.demo.secKillDemo.SeckillUserS.mapper.SeckillUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SeckillUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("nikename", username );
        SeckillUser seckillUser = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(seckillUser)){
            throw  new RuntimeException("用户名密码错误");
        }
        //用户对应的权限信息
        List<String> stringList = new ArrayList<>(Arrays.asList("test","admin"));
        //把数据封装成UserDetails
        return new LoginUser(seckillUser);
    }


}
