package com.dokidoki.userserver.controller;

import com.dokidoki.userserver.componet.JwtProvider;
import com.dokidoki.userserver.dto.GoogleUserInfo;
import com.dokidoki.userserver.dto.KakaoUserInfo;
import com.dokidoki.userserver.dto.response.OauthLoginUrlDto;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class OauthController {

    private final OauthUrlUtil oauthUrlUtil;
    private final OauthService oauthService;

    private final UserService userService;

    private final JwtProvider jwtProvider;

    @Value("${front.redirect_uri}")
    private String FRONT_REDIRECT_URI;

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
    public void redirectGoogle(@RequestParam String code, HttpServletResponse response) throws IOException {
        GoogleUserInfo info = oauthService.getUserInfoGoogle(code);

        // 회원이 존재하면 반환, 아니면 가입 후 반환
        UserEntity user = userService.getUserFromSubAndProvider(info.getSub(), ProviderType.GOOGLE).orElse(
                userService.saveUser(
                        UserEntity.builder()
                                .sub(info.getSub())
                                .email(info.getEmail())
                                .picture(info.getPicture())
                                .name(info.getName())
                                .providerType(ProviderType.GOOGLE)
                                .build())
        );
        String accessToken = jwtProvider.getAccessToken(user.getId());
        String refreshToken = jwtProvider.getRefreshToken(user);

        response.sendRedirect(getFrontRedirectUrl(accessToken, refreshToken));
    }

    @GetMapping("/kakao/redirect")
    public void redirectKakao(@RequestParam String code, HttpServletResponse response) throws IOException {
        KakaoUserInfo info = oauthService.getUserInfoKakao(code);

        // 회원이 존재하면 반환, 아니면 가입 후 반환
        UserEntity user = userService.getUserFromSubAndProvider(info.getSub(), ProviderType.KAKAO).orElse(
                userService.saveUser(
                        UserEntity.builder()
                                .sub(info.getSub())
                                .email(info.getEmail())
                                .picture(info.getPicture())
                                .name(info.getNickname())
                                .providerType(ProviderType.KAKAO)
                                .build())
        );
        String accessToken = jwtProvider.getAccessToken(user.getId());
        String refreshToken = jwtProvider.getRefreshToken(user);

        response.sendRedirect(getFrontRedirectUrl(accessToken, refreshToken));
    }

    private String getFrontRedirectUrl(String accessToken, String refreshToken){
        return FRONT_REDIRECT_URI + "?access_token=" + accessToken + "&refresh_token=" + refreshToken;
    }
}
