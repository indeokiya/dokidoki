package com.dokidoki.userserver.componet;

import com.dokidoki.userserver.dto.OauthToken;
import com.dokidoki.userserver.dto.request.TokenBodyReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
// 설정한 redirect URL로 받아온 code을 통해 토큰을 받아오는 객체
public class CodeToToken {

    private final RestTemplate restTemplate;
    @Value("${oauth2.google.client_id}")
    private String CLIENT_ID_GOOGLE;

    @Value("${oauth2.google.client_secret}")
    private String CLIENT_SECRET_GOOGLE;

    @Value("${oauth2.google.redirect_uri}")
    private String REDIRECT_URI_GOOGLE;

    @Value("${oauth2.google.token_uri}")
    private String TOKEN_URI_GOOGLE;

    @Value("${oauth2.kakao.client_id}")
    private String CLIENT_ID_KAKAO;

    @Value("${oauth2.kakao.client_secret}")
    private String CLIENT_SECRET_KAKAO;

    @Value("${oauth2.kakao.redirect_uri}")
    private String REDIRECT_URI_KAKAO;

    @Value("${oauth2.kakao.token_uri}")
    private String TOKEN_URI_KAKAO;


    public OauthToken codeToTokenGoogle(String code){
        URI uri = UriComponentsBuilder.fromUriString(TOKEN_URI_GOOGLE).build().toUri();

        TokenBodyReq tokenBody = TokenBodyReq.builder()
                .code(code)
                .client_id(CLIENT_ID_GOOGLE)
                .client_secret(CLIENT_SECRET_GOOGLE)
                .grant_type("authorization_code")
                .redirect_uri(REDIRECT_URI_GOOGLE)
                .build();

        HttpEntity<TokenBodyReq> entity = new HttpEntity(tokenBody);

        log.info(uri.toString());
        OauthToken token = restTemplate.postForEntity(uri, entity, OauthToken.class).getBody();
        return token;
    }

    public OauthToken codeToTokenKakao(String code){
        URI uri = UriComponentsBuilder.fromUriString(TOKEN_URI_KAKAO).build().toUri();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("client_id",CLIENT_ID_KAKAO);
        params.add("grant_type","authorization_code");
        params.add("redirect_uri",REDIRECT_URI_KAKAO);
        params.add("code",code);
        params.add("client_secret", CLIENT_SECRET_KAKAO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(params, headers);

        OauthToken token = restTemplate.postForEntity(uri, entity, OauthToken.class).getBody();
        log.info("까까오 아이디 토큰 : " + token.getId_token());
        return token;
    }
}
