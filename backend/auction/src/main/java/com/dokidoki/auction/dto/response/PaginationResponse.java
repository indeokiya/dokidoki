package com.dokidoki.auction.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class PaginationResponse {
    private final Object contents;
    private final Boolean is_last;
}
