package com.example.web.security;

import com.example.web.member.Member;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtProvider {
    private static final String secretKey = "ThisIsA_SecretKeyForJwtExample";

    public static String generateJwtToken(Member member) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(member.getLoginId())
                .setHeader(createHeader())
                .setClaims(createClaims(member))
                .setExpiration(createExpireDateForOneYear())
                .signWith(SignatureAlgorithm.HS256, createSigningKey());

        return builder.compact();
    }

    public static boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);
            log.info("expireTime : {}", claims.getExpiration());
            log.info("id : {}", claims.get("id"));
            log.info("role : {}", claims.get("role"));
            return true;
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            return false;
        } catch (JwtException exception) {
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            log.error("Token is null");
            return false;
        }
    }

    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    private static Date createExpireDateForOneYear() {
        // 토큰 만료시간은 30일으로 설정
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 30);
        return c.getTime();
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }

    private static Map<String, Object> createClaims(Member member) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", member.getLoginId());
        claims.put("role", member.getRole());

        return claims;
    }

    private static Key createSigningKey() {
        byte[] apiKeySecretBytes = secretKey.getBytes();
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private static Claims getClaimsFormToken(String token) {
        return Jwts.parser().setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token).getBody();
    }

    public static String getUserIdFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return (String) claims.get("id");
    }
}
