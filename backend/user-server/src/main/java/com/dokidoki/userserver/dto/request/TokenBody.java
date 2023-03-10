package com.dokidoki.userserver.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
@AllArgsConstructor
public class TokenBody {
    private String client_id;
    private String client_secret;
    private String code;
    private String grant_type;
    private String redirect_uri;
}
