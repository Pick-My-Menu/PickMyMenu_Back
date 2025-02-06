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
import java.util.UUID;

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
    public String generateToken(String email, String name, String role) {
        long expirationTime = 1000 * 60 * 60;  // 1시간 (밀리초 단위)

        return Jwts.builder()
                .setSubject(email)  // 사용자 이메일을 Subject로 설정
                .claim("name", name)
                .claim("role", role)
                .setIssuer(issuer)     // issuer 설정
                .setIssuedAt(new Date())  // 발급 일시
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))  // 만료 일시 (1시간)
                .signWith(secretKey)  // 비밀 키 사용
                .compact();
    }

    // JWT 검증 및 추출
    public String validateAndExtract(String token) {
        Claims claims = Jwts.parserBuilder()  // 변경된 부분
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String findRole(String token) {
        Claims claims = Jwts.parserBuilder()  // 변경된 부분
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("role", String.class);
    }


    // JWT가 만료되었는지 확인하는 메서드
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parserBuilder()  // 변경된 부분
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());  // 만약 만료 시간이 현재 시간 이전이면 true 반환
        } catch (Exception e) {
            return false;  // 예외 발생 시 (예: 잘못된 토큰) false 반환
        }
    }
}
