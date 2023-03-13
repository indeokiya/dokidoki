package com.dokidoki.userserver.repository;

import com.dokidoki.userserver.dto.KakaoUserInfo;
import com.dokidoki.userserver.dto.OauthToken;
import com.dokidoki.userserver.exception.CustomException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.Base64;

@RequiredArgsConstructor
@Repository
@Slf4j
public class KakaoRepository {

    private final ObjectMapper objectMapper;
    public KakaoUserInfo getUserInfoFromToken(OauthToken token) {
        String idTokenPayload = token.getId_token().split("\\.")[1];
        String decodedString = new String(Base64.getDecoder().decode(idTokenPayload.getBytes()));
        log.info("카카오 디코디도 서터링 : " + decodedString);

        try{
            KakaoUserInfo userInfo = objectMapper.readValue(decodedString, KakaoUserInfo.class);
            return userInfo;
        }catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CustomException(HttpStatus.BAD_REQUEST, "카카오 유저 불러오기 실패");
        }
    }
}
