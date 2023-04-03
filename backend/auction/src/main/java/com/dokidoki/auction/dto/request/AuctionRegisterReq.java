package com.dokidoki.auction.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuctionRegisterReq {

    // 판매자id (작성자id)

    private Long product_id;

    private String title;

    private String description;

    private Long offer_price;

    private Long price_size;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime end_at;

    private String meeting_place;

    private MultipartFile[] files;
}
