package com.dokidoki.auction.controller;

import com.dokidoki.auction.common.BaseResponseBody;
import com.dokidoki.auction.common.JWTUtil;
import com.dokidoki.auction.service.InterestService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RequestMapping("/auctions/interests")
@RequiredArgsConstructor
@RestController
public class InterestController {

    private final InterestService interestService;
    private final JWTUtil jwtUtil;

    @PostMapping("")
    public ResponseEntity<BaseResponseBody> addInterest(
            @RequestParam("auction_id") @ApiParam(value = "경매 id", required = true) Long auctionId,
            HttpServletRequest request
    ) {
        Long buyerId = jwtUtil.getUserId(request);
        if (buyerId == null)
            return ResponseEntity.status(400).body(BaseResponseBody.of("토큰이 유효하지 않습니다."));

        if (interestService.addInterest(buyerId, auctionId))
            return ResponseEntity.status(201).body(BaseResponseBody.of("관심목록에 추가되었습니다."));
        else
            return ResponseEntity.status(400).body(BaseResponseBody.of("이미 관심목록에 존재합니다."));
    }

    @DeleteMapping("")
    public ResponseEntity<BaseResponseBody> deleteInterest(
            @RequestParam("auction_id") @ApiParam(value = "경매 id", required = true) Long auctionId,
            HttpServletRequest request) {
        Long buyerId = jwtUtil.getUserId(request);
        if (buyerId == null)
            return ResponseEntity.status(400).body(BaseResponseBody.of("토큰이 유효하지 않습니다."));

        if (interestService.deleteInterest(buyerId, auctionId))
            return ResponseEntity.status(200).body(BaseResponseBody.of("관심 목록에서 해제되었습니다."));
        else
            return ResponseEntity.status(400).body(BaseResponseBody.of("관심 목록에 존재하지 않습니다."));
    }

}
