package com.dokidoki.auction.controller;

import com.dokidoki.auction.dto.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
public class AuctionImagesController {
    @PostMapping("/auctions")
    public ResponseEntity<CommonResponse<?>>
}
