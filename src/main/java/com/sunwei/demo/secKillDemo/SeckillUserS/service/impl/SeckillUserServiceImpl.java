package com.sunwei.demo.secKillDemo.SeckillUserS.service.impl;

import com.sunwei.demo.secKillDemo.SeckillUserS.entity.SeckillUser;
import com.sunwei.demo.secKillDemo.SeckillUserS.mapper.SeckillUserMapper;
import com.sunwei.demo.secKillDemo.SeckillUserS.service.ISeckillUserService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 用户表
 * @Author: 孙伟
 * @Date:   2022-04-26 07:42:268.057
 * @Version: V1.0
 */
@Service
@Primary
public class SeckillUserServiceImpl extends ServiceImpl<SeckillUserMapper, SeckillUser> implements ISeckillUserService {

}
