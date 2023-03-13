package com.dokidoki.userserver.controller;

import com.dokidoki.userserver.dto.GoogleUserInfo;
import com.dokidoki.userserver.dto.KakaoUserInfo;
import com.dokidoki.userserver.dto.response.OauthLoginUrlDto;
import com.dokidoki.userserver.enumtype.ProviderType;
import com.dokidoki.userserver.exception.CustomException;
import com.dokidoki.userserver.service.OauthService;
import com.dokidoki.userserver.util.OauthUrlUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class OauthController {

    private final OauthUrlUtil oauthUrlUtil;
    private final OauthService oauthService;

    @GetMapping("/login/{provider}")
    public ResponseEntity<OauthLoginUrlDto> oauth2LoginUrl(
            @PathVariable(name = "provider") String provider
    ){

        OauthLoginUrlDto response;

        switch (provider){
            case "kakao":
                response = OauthLoginUrlDto.builder()
                        .url(oauthUrlUtil.getAuthorizationUrlKakao())
                        .provider(ProviderType.KAKAO).build();
                break;
            case "google":
                response = OauthLoginUrlDto.builder()
                        .url(oauthUrlUtil.getAuthorizationUrlGoogle())
                        .provider(ProviderType.GOOGLE).build();
                break;
            default:
                throw new CustomException(HttpStatus.BAD_REQUEST, "잘못 된 요청입니다.");
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping("/google/redirect")
    public ResponseEntity<?> redirectGoogle(@RequestParam String code, HttpServletResponse response){
        GoogleUserInfo info = oauthService.getUserInfoGoogle(code);
        return ResponseEntity.ok(info);
    }

    @GetMapping("/kakao/redirect")
    public ResponseEntity<?> redirectKakao(@RequestParam String code, HttpServletResponse response){
        log.info("kakao code: " + code);
        KakaoUserInfo info = oauthService.getUserInfoKakao(code);
        return ResponseEntity.ok(info);
    }
}
