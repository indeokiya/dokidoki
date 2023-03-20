package com.dokidoki.auction.controller;

import com.dokidoki.auction.dto.response.CommonResponse;
import com.dokidoki.auction.dto.response.SimpleAuctionEndInfo;
import com.dokidoki.auction.service.AuctionListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/auction-lists")
@RequiredArgsConstructor
public class AuctionListController {
    private final AuctionListService auctionListService;

    @GetMapping("/end")
    public ResponseEntity<CommonResponse<List<SimpleAuctionEndInfo>>> readSimpleAuctionEnds(Pageable pageable) {
        // 데이터 조회
        List<SimpleAuctionEndInfo> simpleAuctionEndInfos = auctionListService.readSimpleAuctionEnds(pageable);

        return new ResponseEntity<>(
                CommonResponse.of(200, "성공", simpleAuctionEndInfos),
                HttpStatus.OK
        );
    }
}
