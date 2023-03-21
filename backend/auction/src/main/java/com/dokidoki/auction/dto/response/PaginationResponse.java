package com.dokidoki.auction.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PaginationResponse<D> {
    private final D contents;
    private final Boolean is_last;
}
