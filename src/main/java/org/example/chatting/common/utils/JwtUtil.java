package org.example.chatting.common.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class JwtUtil {

    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret.key}")
    private String secretKeyString;

    private SecretKey key;
    private JwtParser parser;

    @PostConstruct
    public void init() {
        byte[] bytes = Decoders.BASE64.decode(secretKeyString);
        this.key = Keys.hmacShaKeyFor(bytes);
        this.parser = Jwts.parser()
            .verifyWith(this.key)
            .build();
    }


    // 토큰 생성
    public String generateToken(long userId) {
        return BEARER_PREFIX + Jwts.builder()
            .claim("userId", userId)
            .signWith(key, Jwts.SIG.HS256)
            .compact();
    }


    public Long getUserId(String token) {
        return parser.parseSignedClaims(token).getPayload().get("userId", Long.class);
    }


}