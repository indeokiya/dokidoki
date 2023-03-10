package com.dokidoki.userserver.service;

import com.dokidoki.userserver.componet.CodeToTokenGoogle;
import com.dokidoki.userserver.dto.GoogleUserInfo;
import com.dokidoki.userserver.dto.OauthToken;
import com.dokidoki.userserver.repository.GoogleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class OauthService {
    private final CodeToTokenGoogle codeToTokenGoogle;
    private final GoogleRepository googleRepository;

    public GoogleUserInfo getUserInfoGoogle(String code){
        OauthToken token = codeToTokenGoogle.codeToToken(code);
        log.info(token.getAccess_token());
        return googleRepository.getUserInfoFromToken(token);
    }
}
