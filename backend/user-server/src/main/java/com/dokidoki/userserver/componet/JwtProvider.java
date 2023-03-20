package com.dokidoki.userserver.componet;


import com.dokidoki.userserver.entity.UserEntity;
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
public class JwtProvider {
    private Key SECRET_KEY;
    private long ACCESS_EXPIRATION_TIME;
    private long REFRESH_EXPIRATION_TIME;

    private String ISSUER;


    @Autowired
    private JwtProvider(@Value("${jwt.secret}") String secretKey,
                        @Value("${jwt.access_expiration}") long ecc_expirationTime,
                        @Value("${jwt.refresh_expiration}") long ref_expirationTime,
                        @Value("${jwt.issuer}") String issuer){
        SECRET_KEY = Keys.hmacShaKeyFor(secretKey.getBytes());
        ACCESS_EXPIRATION_TIME = ecc_expirationTime;
        REFRESH_EXPIRATION_TIME = ref_expirationTime;
        ISSUER = issuer;
    };
    public String getAccessToken(Long userId){
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

    public String getRefreshToken(UserEntity user){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(ISSUER)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_EXPIRATION_TIME))
                .claim("user_id", user.getId())
                .claim("name",user.getName())
                .claim("email",user.getEmail())
                .claim("picture",user.getPicture())
                .claim("provider",user.getProviderType())
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getToken(HttpServletRequest request){
        return request.getHeader("Authorization").substring(7);
    }
    public Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
