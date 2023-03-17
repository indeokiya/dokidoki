package com.dokidoki.auction.controller;

import com.dokidoki.auction.common.BaseResponseBody;
import com.dokidoki.auction.common.JWTutil;
import com.dokidoki.auction.service.InterestService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("/auctions/interests")
@RequiredArgsConstructor
@RestController
public class InterestController {

    private final InterestService interestService;
    private final JWTutil jwTutil;

    @PostMapping("")
    public ResponseEntity<BaseResponseBody> addInterest(
            @RequestParam("auction_id") @ApiParam(value = "경매 id", required = true) Long auctionId
//            HttpServletRequest request
    ) {
//        Long buyerId = jwTutil.getUserId(request);
        Long buyerId = 1L;
        interestService.addInterest(buyerId, auctionId);
        return ResponseEntity.status(201).body(BaseResponseBody.of("관심목록에 추가되었습니다."));
    }

    @DeleteMapping("")
    public ResponseEntity<BaseResponseBody> deleteInterest(
            @RequestParam("auction_id") @ApiParam(value = "경매 id", required = true) Long auctionId) {

        Long buyerId = 1L;
        if (interestService.deleteInterest(buyerId, auctionId))
            return ResponseEntity.status(200).body(BaseResponseBody.of("관심 목록에서 해제되었습니다."));
        else
            return ResponseEntity.status(400).body(BaseResponseBody.of("관심 목록에 존재하지 않습니다."));
    }

}
