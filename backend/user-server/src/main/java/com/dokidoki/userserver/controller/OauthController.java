package com.dokidoki.userserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class OauthController {

    @GetMapping()
    public void hi(HttpServletRequest request){

    }

    @GetMapping("/login/google")
    public ResponseEntity<String> oauth2LoginGoogle(@RequestParam String code){
        return ResponseEntity.ok(code);
    }
}
