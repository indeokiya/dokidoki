package com.dokidoki.userserver.controller;

import com.dokidoki.userserver.componet.JwtProvider;
import com.dokidoki.userserver.dto.response.JWTRes;
import com.dokidoki.userserver.entity.UserEntity;
import com.dokidoki.userserver.exception.CustomException;
import com.dokidoki.userserver.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/tokens")
@RequiredArgsConstructor
public class TokenController {

    private final JwtProvider jwtProvider;
    private final UserService userService;

    @GetMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request){
        String token = jwtProvider.getToken(request);
        Long userId = ((Integer)jwtProvider.parseClaims(token).get("user_id")).longValue();

        UserEntity userEntity = userService.getUserById(userId).orElseThrow(
                ()-> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.")
        );

        String accessToken = jwtProvider.getAccessToken(userEntity.getId());
        String refreshToekn = jwtProvider.getRefreshToken(userEntity);

        return ResponseEntity.ok(JWTRes.builder()
                .access_token(accessToken)
                .refresh_token(refreshToekn));
    }
}
