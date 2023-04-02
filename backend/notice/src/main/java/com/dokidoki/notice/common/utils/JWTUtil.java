package com.dokidoki.notice.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JWTUtil {
    private static Key SECRET_KEY;
    private static long ACCESS_EXPIRATION_TIME;
    private static long REFRESH_EXPIRATION_TIME;

    private static String ISSUER;

    @Autowired
    private JWTUtil(@Value("${jwt.secret}") String secretKey,
                    @Value("${jwt.access_expiration}") long ecc_expirationTime,
                    @Value("${jwt.refresh_expiration}") long ref_expirationTime,
                    @Value("${jwt.issuer}") String issuer) {
        SECRET_KEY = Keys.hmacShaKeyFor(secretKey.getBytes());
        ACCESS_EXPIRATION_TIME = ecc_expirationTime;
        REFRESH_EXPIRATION_TIME = ref_expirationTime;
        ISSUER = issuer;
    };

    public static Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public static Long getUserId(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isBlank())
            return null;

        // Bearer parsing
        String accessToken = authorizationHeader.substring(7);
        return parseClaims(accessToken).get("user_id", Long.class);
    }

    public static String getAccessToken(Long userId) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(ISSUER)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_EXPIRATION_TIME))
                .claim("user_id", userId)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

}
