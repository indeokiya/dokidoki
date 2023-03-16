package com.dokidoki.auction.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class AuctionImageResponse {
    private final Long auction_id;
    private final List<String> image_urls;
}
