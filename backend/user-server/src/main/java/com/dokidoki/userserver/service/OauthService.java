package com.dokidoki.userserver.service;

import com.dokidoki.userserver.componet.CodeToToken;
import com.dokidoki.userserver.dto.GoogleUserInfo;
import com.dokidoki.userserver.dto.KakaoUserInfo;
import com.dokidoki.userserver.dto.OauthToken;
import com.dokidoki.userserver.repository.GoogleRepository;
import com.dokidoki.userserver.repository.KakaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class OauthService {
    private final CodeToToken codeToToken;
    private final GoogleRepository googleRepository;

    private final KakaoRepository kakaoRepository;

    public GoogleUserInfo getUserInfoGoogle(String code){
        OauthToken token = codeToToken.codeToTokenGoogle(code);
        log.info(token.getAccess_token());
        return googleRepository.getUserInfoFromToken(token);
    }

    public KakaoUserInfo getUserInfoKakao(String code){
        OauthToken token = codeToToken.codeToTokenKakao(code);

        return kakaoRepository.getUserInfoFromToken(token);
    }
}
