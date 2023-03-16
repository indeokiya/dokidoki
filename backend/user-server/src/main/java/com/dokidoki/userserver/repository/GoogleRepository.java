package com.dokidoki.userserver.repository;

import com.dokidoki.userserver.dto.GoogleUserInfo;
import com.dokidoki.userserver.dto.OauthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Repository
@Slf4j
public class GoogleRepository {
    private final RestTemplate restTemplate;

    @Value("${oauth2.google.profile_uri}")
    private String PROFILE_URI;

    public GoogleUserInfo getUserInfoFromToken(OauthToken token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token.getAccess_token());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<GoogleUserInfo> res = restTemplate.exchange(PROFILE_URI, HttpMethod.GET ,entity, GoogleUserInfo.class);

        return res.getBody();
    }
}
