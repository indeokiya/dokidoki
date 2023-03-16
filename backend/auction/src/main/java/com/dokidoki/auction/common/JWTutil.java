//package com.dokidoki.auction.common;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import java.security.Key;
//@Component
//@Slf4j
//public class JWTutil {
//    private Key SECRET_KEY;
//    private long ACCESS_EXPIRATION_TIME;
//    private long REFRESH_EXPIRATION_TIME;
//
//    private String ISSUER;
//
//    @Autowired
//    private JWTutil(@Value("${jwt.secret}") String secretKey,
//                        @Value("${jwt.access_expiration}") long ecc_expirationTime,
//                        @Value("${jwt.refresh_expiration}") long ref_expirationTime,
//                        @Value("${jwt.issuer}") String issuer){
//        SECRET_KEY = Keys.hmacShaKeyFor(secretKey.getBytes());
//        ACCESS_EXPIRATION_TIME = ecc_expirationTime;
//        REFRESH_EXPIRATION_TIME = ref_expirationTime;
//        ISSUER = issuer;
//    };
//
//    public Claims parseClaims(String token) {
//        try {
//            return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
//        } catch (ExpiredJwtException e) {
//            return e.getClaims();
//        }
//    }
//
//    public Long getUserId(HttpServletRequest request){
//        // Bearer parsing
//        String accessToken = request.getHeader("Authorization").substring(7);
//        return parseClaims(accessToken).get("user_id", Long.class);
//    }
//}
