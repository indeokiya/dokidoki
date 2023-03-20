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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    @DeleteMapping
    public ResponseEntity<?> refresh(HttpServletRequest request){
        String token = jwtProvider.getToken(request);
        Long userId = ((Integer)jwtProvider.parseClaims(token).get("user_id")).longValue();

        log.info("유저 아이디 " + userId);

       userService.deleteById(userId);

       return ResponseEntity.noContent().build();
    }
}
