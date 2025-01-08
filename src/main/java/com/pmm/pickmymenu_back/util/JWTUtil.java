package com.pmm.pickmymenu_back.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.UUID;  // UUID 임포트

@Component
public class JWTUtil {

    @Value("${security.jwt.secret-key}")
    private String secretKeyString;  // application.yml에서 가져올 secret-key

    @Value("${security.jwt.issuer}")
    private String issuer;  // application.yml에서 가져올 issuer

    private Key secretKey;

    // 초기화 시 secretKey를 생성하여 저장
    @PostConstruct
    public void init() {
        // UUID로 secretKey를 생성
        if (secretKeyString == null || secretKeyString.isEmpty()) {
            secretKeyString = UUID.randomUUID().toString();  // secretKey를 UUID로 설정
        }
        this.secretKey = new SecretKeySpec(secretKeyString.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    // JWT 생성
    public String generateToken(String username) {
        long expirationTime = 1000 * 60 * 60;  // 1시간 (밀리초 단위)

        return Jwts.builder()
                .setSubject(username)  // 사용자 이름을 Subject로 설정
                .setIssuer(issuer)     // issuer 설정
                .setIssuedAt(new Date())  // 발급 일시
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))  // 만료 일시 (1시간)
                .signWith(secretKey)  // 비밀 키 사용
                .compact();
    }

    // JWT 검증 및 추출
    public String validateAndExtract(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();  // 토큰에서 사용자 이름 반환
    }
}
