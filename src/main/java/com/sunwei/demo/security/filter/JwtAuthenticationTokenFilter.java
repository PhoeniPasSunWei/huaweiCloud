package com.sunwei.demo.security.filter;

import com.sunwei.demo.secKillDemo.SeckillUserS.entity.SeckillUser;
import com.sunwei.demo.secKillDemo.SeckillUserS.mapper.SeckillUserMapper;
import com.sunwei.demo.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private SeckillUserMapper userMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //获取token
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)){
            //放行
            filterChain.doFilter(request,response);
            return;
        }
        //解析token
        try {
            Claims claims =  JwtUtil.parseJWT(token);
            //String userId = claims.getSubject(); //教程上是这样
            String userId = claims.get("jti").toString();
            //获取用户信息 后面用redis 缓存替换
            System.out.println("===================userId==================="+userId);
            SeckillUser seckillUser = userMapper.selectById(userId);
            if (Objects.isNull(seckillUser) || "zhangSanOut".equals(seckillUser.getNikename())){
                //下面自己写的不是正式的 用redis 正常缓存处理
                seckillUser.setNikename("zhangSan");
                userMapper.updateById(seckillUser);
                throw new RuntimeException("用户未登录");
            }
            //存入securityContextHolder  security其他的filter要从 这个对象securityContextHolder获取用户信息
            UsernamePasswordAuthenticationToken token1 = new UsernamePasswordAuthenticationToken(
                    seckillUser, null,null);
            SecurityContextHolder.getContext().setAuthentication(token1);
            filterChain.doFilter(request,response);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("非法token");
        }
    }

}
