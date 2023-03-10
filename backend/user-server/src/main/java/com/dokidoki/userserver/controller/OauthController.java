package com.dokidoki.userserver.controller;

import com.dokidoki.userserver.dto.GoogleUserInfo;
import com.dokidoki.userserver.dto.response.OauthLoginUrlDto;
import com.dokidoki.userserver.enumtype.ProviderType;
import com.dokidoki.userserver.service.OauthService;
import com.dokidoki.userserver.util.OauthUrlUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/login/google")
    public ResponseEntity<OauthLoginUrlDto> oauth2LoginGoogle(){

        OauthLoginUrlDto response = OauthLoginUrlDto.builder()
                .url(oauthUrlUtil.getAuthorizationUrlGoogle())
                .provider(ProviderType.GOOGLE).build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/google/redirect")
    public ResponseEntity<?> redirectGoogle(@RequestParam String code, HttpServletResponse response){
        GoogleUserInfo info = oauthService.getUserInfoGoogle(code);



        return ResponseEntity.ok(info);
    }
}
