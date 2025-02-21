package com.lzk.tools;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Map;

/**
 * Jwt工具类
 * makeJwt: 用于生成用户的JWT令牌 => String
 * parseJwt: 用于验证(解析)用户的JWT令牌 => Claims
 */

@Component
public class JwtTool {

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.AcTime}")
    private long AcTime;
    public String makeJwt(Map<String, Object> data)
    {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .setClaims(data)
                .setExpiration(new Date(System.currentTimeMillis() + AcTime*1000))
                .compact();
    }

    public Claims parseJwt(String jwt)
    {
        return Jwts.parser()
                .setSigningKey(key.getBytes())
                .parseClaimsJws(jwt)
                .getBody();
    }

    public static void main(String[] args) {

    }
}
