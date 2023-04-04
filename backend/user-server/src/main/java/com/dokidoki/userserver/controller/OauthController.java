package com.dokidoki.userserver.controller;

import com.dokidoki.userserver.componet.JwtProvider;
import com.dokidoki.userserver.dto.GoogleUserInfo;
import com.dokidoki.userserver.dto.KakaoUserInfo;
import com.dokidoki.userserver.dto.response.OauthLoginUrlRes;
import com.dokidoki.userserver.entity.UserEntity;
import com.dokidoki.userserver.enumtype.ProviderType;
import com.dokidoki.userserver.exception.CustomException;
import com.dokidoki.userserver.service.OauthService;
import com.dokidoki.userserver.service.UserService;
import com.dokidoki.userserver.util.OauthUrlUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@Slf4j
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class OauthController {

    private final OauthUrlUtil oauthUrlUtil;
    private final OauthService oauthService;

    private final UserService userService;

    private final JwtProvider jwtProvider;

    private final Long DEFAULT_POINT = (long)0x7fffffff;

    @Value("${front.redirect_uri}")
    private String FRONT_REDIRECT_URI;

    // 구글, 카카오 로그인 url 반환
    @GetMapping("/login/{provider}")
    public ResponseEntity<OauthLoginUrlRes> oauth2LoginUrl(
            @PathVariable(name = "provider") String provider
    ){

        OauthLoginUrlRes response;

        switch (provider){
            case "kakao":
                response = OauthLoginUrlRes.builder()
                        .url(oauthUrlUtil.getAuthorizationUrlKakao())
                        .provider(ProviderType.KAKAO).build();
                break;
            case "google":
                response = OauthLoginUrlRes.builder()
                        .url(oauthUrlUtil.getAuthorizationUrlGoogle())
                        .provider(ProviderType.GOOGLE).build();
                break;
            default:
                throw new CustomException(HttpStatus.BAD_REQUEST, "잘못 된 요청입니다.");
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping("/google/redirect")
    public void redirectGoogle(@RequestParam String code, HttpServletResponse response) throws IOException {
        GoogleUserInfo info = oauthService.getUserInfoGoogle(code);

        // 회원이 존재하면 반환, 아니면 가입 후 반환
        UserEntity user = userService.getUserFromSubAndProvider(info.getSub(), ProviderType.GOOGLE).orElse(null);

        if(user == null){
                user = userService.saveUser(
                        UserEntity.builder()
                                .sub(info.getSub())
                                .email(info.getEmail())
                                .picture(info.getPicture())
                                .name(info.getName())
                                .providerType(ProviderType.GOOGLE)
                                .point(DEFAULT_POINT)
                                .build());
        }

        String accessToken = user.getEndTimeOfSuspension().toString();
        String refreshToken = null;
        // 정지 받은 적 없거나 정지 기한이 지났으면 토큰 발급
        if(user.getEndTimeOfSuspension() == null || user.getEndTimeOfSuspension().compareTo(LocalDateTime.now()) < 0) {
            // 토큰 생성
            accessToken = jwtProvider.getAccessToken(user.getId());
            refreshToken = jwtProvider.getRefreshToken(user);
        }

        response.sendRedirect(getFrontRedirectUrl(accessToken, refreshToken));
    }

    @GetMapping("/kakao/redirect")
    public void redirectKakao(@RequestParam String code, HttpServletResponse response) throws IOException {
        KakaoUserInfo info = oauthService.getUserInfoKakao(code);

        // 회원이 존재하면 반환, 아니면 가입 후 반환
        UserEntity user = userService.getUserFromSubAndProvider(info.getSub(), ProviderType.KAKAO).orElse(null);

        if(user == null){
            user = userService.saveUser(
                    UserEntity.builder()
                            .sub(info.getSub())
                            .email(info.getEmail())
                            .picture(info.getPicture())
                            .name(info.getNickname())
                            .providerType(ProviderType.KAKAO)
                            .point(DEFAULT_POINT)
                            .build());
        }
        // 토큰 생성
        String accessToken = jwtProvider.getAccessToken(user.getId());
        String refreshToken = jwtProvider.getRefreshToken(user);

        response.sendRedirect(getFrontRedirectUrl(accessToken, refreshToken));
    }

    // query string에 토큰을 담아 front page redirect
    private String getFrontRedirectUrl(String accessToken, String refreshToken){
        return FRONT_REDIRECT_URI + "?access_token=" + accessToken + "&refresh_token=" + refreshToken;
    }
}
