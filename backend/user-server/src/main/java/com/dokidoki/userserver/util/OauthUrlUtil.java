package com.dokidoki.userserver.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class OauthUrlUtil {

    private final String AUTHORIZATION_URI_GOOGLE = "https://accounts.google.com/o/oauth2/v2/auth";

    private final String AUTHORIZATION_URI_KAKAO = "https://kauth.kakao.com/oauth/authorize";
    @Value("${oauth2.google.client_id}")
    private String CLIENT_ID_GOOGLE;

    @Value("${oauth2.google.redirect_uri}")
    private String REDIRECT_URI_GOOGLE;

    @Value("${oauth2.kakao.client_id}")
    private String CLIENT_ID_KAKAO;

    @Value("${oauth2.kakao.redirect_uri}")
    private String REDIRECT_URI_KAKAO;

    public String getAuthorizationUrlGoogle(){
        return UriComponentsBuilder.fromUriString(AUTHORIZATION_URI_GOOGLE)
                .queryParam("client_id", CLIENT_ID_GOOGLE)
                .queryParam("redirect_uri", REDIRECT_URI_GOOGLE)
                .queryParam("response_type", "code")
                .queryParam("scope", "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email")
                .build().toUri().toString();
    }

    public String getAuthorizationUrlKakao(){
        return UriComponentsBuilder.fromUriString(AUTHORIZATION_URI_KAKAO)
                .queryParam("client_id", CLIENT_ID_KAKAO)
                .queryParam("redirect_uri", REDIRECT_URI_KAKAO)
                .queryParam("response_type", "code")
                .build().toUri().toString();
    }
}
