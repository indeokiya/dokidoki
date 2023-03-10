package com.dokidoki.userserver.componet;

import antlr.Token;
import com.dokidoki.userserver.dto.OauthToken;
import com.dokidoki.userserver.dto.request.TokenBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
public class CodeToTokenGoogle {

    private final RestTemplate restTemplate;
    @Value("${oauth2.google.client_id}")
    private String CLIENT_ID_GOOGLE;

    @Value("${oauth2.google.client_secret}")
    private String CLIENT_SECRET_GOOGLE;

    @Value("${oauth2.google.redirect_uri}")
    private String REDIRECT_URI_GOOGLE;

    private final String TOKEN_URI_GOOGLE = "https://oauth2.googleapis.com/token";

    public OauthToken codeToToken(String code){
        URI uri = UriComponentsBuilder.fromUriString(TOKEN_URI_GOOGLE)
                .queryParam("client_id", CLIENT_ID_GOOGLE)
                .queryParam("client_secret", CLIENT_SECRET_GOOGLE)
                .queryParam("code", code)
                .queryParam("grant_type", "authorization_code")
                .queryParam("redirect_uri", REDIRECT_URI_GOOGLE).build().toUri();

        TokenBody body = TokenBody.builder()
                .code(code)
                .client_id(CLIENT_ID_GOOGLE)
                .client_secret(CLIENT_SECRET_GOOGLE)
                .grant_type("authorization_code")
                .redirect_uri(REDIRECT_URI_GOOGLE)
                .build();

        HttpEntity<TokenBody> entity = new HttpEntity(body);

        log.info(uri.toString());
        OauthToken token = restTemplate.postForEntity(uri, entity, OauthToken.class).getBody();
        return token;
    }
}
