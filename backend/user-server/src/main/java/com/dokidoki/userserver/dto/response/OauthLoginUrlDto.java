package com.dokidoki.userserver.dto.response;

import com.dokidoki.userserver.enumtype.ProviderType;
import lombok.*;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class OauthLoginUrlDto {
    ProviderType provider;
    String url;
}
