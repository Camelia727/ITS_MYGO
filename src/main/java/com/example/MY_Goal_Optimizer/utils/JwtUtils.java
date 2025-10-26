package com.example.MY_Goal_Optimizer.utils;

import com.example.MY_Goal_Optimizer.exception.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import java.security.Key;
import java.util.Date;

/**
 * JwtUtils
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/24 下午5:21
 */
public class JwtUtils {

    // 密钥
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 解析token
    public static Claims parseToken(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }

    // 从token中获取用户ID
    public static Long getUserIdFromToken(String token) {
        String userIdStr = parseToken(token).getSubject();
        return Long.parseLong(userIdStr);
    }

    // 从用户ID生成对应token
    public static String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000*7))
                .signWith(SECRET_KEY)
                .compact();
    }

    // 验证token是否有效
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            throw new TokenException(401, "token无效！（签名错误）");
        } catch (MalformedJwtException e) {
            throw new TokenException(404, "token无效！（格式错误）");
        } catch (ExpiredJwtException e) {
            throw new TokenException(401, "token已过期，请重新登录！");
        } catch (UnsupportedJwtException e) {
            throw new TokenException(404, "token无效！（格式不支持）");
        } catch (IllegalArgumentException e) {
            throw new TokenException(401, "token为空，请先登录！");
        }
    }

}