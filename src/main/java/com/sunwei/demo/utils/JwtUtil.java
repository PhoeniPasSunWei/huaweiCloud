package com.sunwei.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Slf4j
public class JwtUtil {

//    SigningKey: mengxuegu # jwt令牌密钥
//    expires: 604800 # 单位秒，7天

    private static String SigningKey = "willSun";
    /**
     * 生成JWT
     * @param userId
     */
    public static String createJWT(String userId) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(userId)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, SigningKey);
        if (604800 > 0) {
            // expires乘以1000是毫秒转秒
            builder.setExpiration(new Date(nowMillis + 604800*1000));
        }
        return builder.compact();
    }

    public static Claims parseJWT(String jwtToken) {
        Claims claims = Jwts.parser().setSigningKey(SigningKey)//签名必须和生成时的一样 签名是signwith方法参数
                .parseClaimsJws(jwtToken).getBody();
        return claims;
    }
}
