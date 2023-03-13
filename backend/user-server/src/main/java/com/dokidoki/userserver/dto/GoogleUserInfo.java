package com.dokidoki.userserver.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GoogleUserInfo {

    private String sub;
    private String name;
    private String picture;
    private String email;
    private Boolean email_verified;
}
