package com.dokidoki.auction.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AuctionImagesRequest {
    private Long auction_id;
    private MultipartFile[] files;
}
