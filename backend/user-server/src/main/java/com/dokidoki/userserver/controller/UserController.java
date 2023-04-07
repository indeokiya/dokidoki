package com.dokidoki.userserver.controller;

import com.dokidoki.userserver.componet.JwtProvider;

import com.dokidoki.userserver.dto.response.CommonResponse;

import com.dokidoki.userserver.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    @DeleteMapping
    public ResponseEntity<?> deleteUser(HttpServletRequest request){
        String token = jwtProvider.getToken(request);
        Long userId = ((Integer)jwtProvider.parseClaims(token).get("user_id")).longValue();

        log.info("유저 아이디 " + userId);

       userService.deleteById(userId);

       return ResponseEntity.noContent().build();
    }

    /*
        사용자 프로필 사진 관련 API
     */
    @GetMapping("/profiles")
    public ResponseEntity<CommonResponse<String>> readProfileImage(HttpServletRequest request) {
        // 토큰에서 사용자 아이디 추출
        String token = jwtProvider.getToken(request);
        Long userId = ((Integer)jwtProvider.parseClaims(token).get("user_id")).longValue();

        // 사용자가 가진 프로필 조회
        String url = userService.readProfileImage(userId);

        // 프로필 조회에 실패했을 경우,
        if (url == null) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "사용자가 없거나 프로필을 가져오는 데 실패했습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity<>(
                CommonResponse.of(200, "성공", url),
                HttpStatus.OK
        );
    }

    @PutMapping("/profiles")
    public ResponseEntity<CommonResponse<String>> createProfileImage(HttpServletRequest request) {
        // 토큰에서 사용자 아이디 추출
        String token = jwtProvider.getToken(request);
        Long userId = ((Integer)jwtProvider.parseClaims(token).get("user_id")).longValue();

        // 전달받은 파일 확인
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");

        if (multipartFile == null) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "요청받은 파일이 없습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        // 프로필 사진 등록
        String profileImageUrl = userService.createProfileImage(userId, multipartFile);

        // 프로필 사진 등록에 실패했을 경우,
        if (profileImageUrl == null) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "프로필 사진 등록에 실패했습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity<>(
                CommonResponse.of(201, "프로필 사진이 등록되었습니다.", profileImageUrl),
                HttpStatus.CREATED
        );
    }
}
